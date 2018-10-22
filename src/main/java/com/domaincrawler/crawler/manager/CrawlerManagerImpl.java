package com.domaincrawler.crawler.manager;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class CrawlerManagerImpl implements CrawlerManager {

    private final HashSet<String> detectedUrls = new HashSet<>();

    private final HashSet<String> visitedUrls = new HashSet<>();

    @Override
    public HashSet<String> listAllDetectedUrls(final URL url) throws IOException {
        Connection connection = Jsoup.connect(url.toString()).userAgent(USER_AGENT);
        Document mainDocument = connection.get();
        crawlDocument(mainDocument);
        return new HashSet<>();
    }

    private void crawlDocument(final Document document) {
        Elements links = document.select("a[href]");
        Elements media = document.select("[src]");
        Elements imports = document.select("link[href]");
    }
}
