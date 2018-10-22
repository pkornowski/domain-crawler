package com.domaincrawler.crawler.manager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class CrawlerManagerImpl implements CrawlerManager {

    Logger logger = LoggerFactory.getLogger(CrawlerManagerImpl.class);

    private final HashSet<String> detectedUrls = new HashSet<>();

    private final HashSet<String> visitedUrls = new HashSet<>();

    private final HashSet<String> resources = new HashSet<>();

    private String mainDomain;

    @Override
    public HashSet<String> listAllDetectedUrls(final URL url) {
        mainDomain = url.toString();
        crawlDomain(url.toString());
        return resources;
    }

    private void crawlDomain(final String url) {
        final Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
        final Document document;
        try {
            document = connection.get();

            final Elements links = document.select("a[href]");
            this.visitedUrls.add(url);
            this.detectedUrls.addAll(convertElementsToUrl(links, "abs:href"));
            detectedUrls.addAll(
                    links.stream()
                            .filter(link -> link.attr("abs:href").contains(mainDomain))
                            .map(link -> link.attr("abs:href"))
                            .collect(Collectors.toSet())
            );
            visitedUrls.addAll(
                    links.stream()
                            .filter(link -> !link.attr("abs:href").contains(mainDomain))
                            .map(link -> link.attr("abs:href"))
                            .collect(Collectors.toSet())
            );
            detectedUrls.forEach(element -> {
                if (!visitedUrls.contains(element)) {
                    crawlDomain(element);
                }
            });

            fetchResourceLinks(document);
        } catch (IOException e) {
            logger.warn("There was problem with fetch document from url {}. Saved as visited URL.", url);
            visitedUrls.add(url);
        }
    }

    private void fetchResourceLinks(final Document document) {
        Elements media = document.select("[src]");
        Elements imports = document.select("link[href]");
        this.resources.addAll(convertElementsToUrl(media, "abs:src"));
        this.resources.addAll(convertElementsToUrl(imports, "abs:href"));
    }

    private Set<String> convertElementsToUrl(final Elements elements, final String selector) {
        return elements.stream().map(element -> element.attr(selector)).collect(Collectors.toSet());
    }
}
