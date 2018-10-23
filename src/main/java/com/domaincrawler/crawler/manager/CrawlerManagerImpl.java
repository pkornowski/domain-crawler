package com.domaincrawler.crawler.manager;

import com.domaincrawler.crawler.model.Page;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class CrawlerManagerImpl implements CrawlerManager {
    Logger logger = LoggerFactory.getLogger(CrawlerManagerImpl.class);

    private static final String LINK_SELECTOR = "abs:href";

    private static final String MEDIA_SELECTOR = "abs:src";

    private static final String IMPORT_SELECTOR = "link[href]";

    private HashSet<String> resources;

    private HashSet<String> visitedUrls;

    private String mainDomain;

    @Override
    public Page listAllDetectedUrls(final URL url) {
        resources = new HashSet<>();
        visitedUrls = new HashSet<>();
        mainDomain = url.toString();
        final Page mainPage = Page.builder().link(mainDomain).inDomain(true).build();
        crawlPage(mainPage);
        mainPage.setResources(resources);
        return mainPage;
    }

    private Page crawlPage(final Page parentPage) {
        final Connection connection = Jsoup.connect(parentPage.getLink()).userAgent(USER_AGENT);
        final Document document;
        try {
            logger.debug("Start looking for link and resources in {}.", parentPage.getLink());
            document = connection.get();
            this.visitedUrls.add(parentPage.getLink());
            final Elements links = document.select("a[href]");
            final HashSet<Page> childPages = new HashSet<>();

            links.stream().filter(isNotVisitedLink())
                    .forEach(link -> addChildPage(link, childPages));
            parentPage.setChildPages(childPages);

            parentPage.getChildPages().stream()
                    .filter(isNotVisitedAndIsInMainDomain())
                    .forEach(this::crawlPage);
            fetchResourceLinks(document);
            logger.debug("End looking for link and resources in {}.", parentPage.getLink());
        } catch (IOException e) {
            logger.warn("There was problem with fetch document from url {}. Saved as visited URL.", parentPage.getLink());
            visitedUrls.add(parentPage.getLink());
        }
        return parentPage;
    }

    private Predicate<Element> isNotVisitedLink() {
        return link -> !this.visitedUrls.contains(link.attr(LINK_SELECTOR));
    }

    private Predicate<Page> isNotVisitedAndIsInMainDomain() {
        return page -> !visitedUrls.contains(page.getLink()) && page.isInDomain();
    }

    private void addChildPage(final Element link, final HashSet<Page> childPages) {
        Page.PageBuilder builder = Page.builder().link(link.attr(LINK_SELECTOR));
        if (link.attr(LINK_SELECTOR).contains(mainDomain)) {
            builder.inDomain(true);
        } else {
            this.visitedUrls.add(link.attr(LINK_SELECTOR));
            builder.inDomain(false);
        }
        childPages.add(builder.build());
    }

    private void fetchResourceLinks(final Document document) {
        final Elements media = document.select("[src]");
        final Elements imports = document.select(IMPORT_SELECTOR);
        this.resources.addAll(convertElementsToUrl(media, MEDIA_SELECTOR));
        this.resources.addAll(convertElementsToUrl(imports, LINK_SELECTOR));
    }

    private Set<String> convertElementsToUrl(final Elements elements, final String selector) {
        return elements.stream().map(element -> element.attr(selector)).collect(Collectors.toSet());
    }
}
