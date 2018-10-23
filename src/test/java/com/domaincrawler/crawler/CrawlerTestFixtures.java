package com.domaincrawler.crawler;

import com.domaincrawler.crawler.model.Page;

import java.util.Collections;
import java.util.HashSet;

public class CrawlerTestFixtures {

    public static Page prepareExpectedPageResult() {
        final Page childPage = Page.builder()
                .inDomain(false)
                .link("http://www.iana.org/domains/example")
                .build();
        return Page.builder()
                .link("http://www.example.com")
                .inDomain(true)
                .childPages(new HashSet<>(Collections.singleton(childPage)))
                .resources(new HashSet<>())
                .build();
    }
}
