package com.domaincrawler.crawler.manager;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

@Component
public class CrawlerManagerImpl implements CrawlerManager {

    private final HashSet<String> detectedUrls = new HashSet<>();

    private final HashSet<String> visitedUrls = new HashSet<>();

    @Override
    public HashSet<String> listAllDetectedUrls(final URL url) throws IOException {
        return new HashSet<>();
    }

}
