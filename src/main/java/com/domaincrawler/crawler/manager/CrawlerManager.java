package com.domaincrawler.crawler.manager;

import com.domaincrawler.crawler.model.Page;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

/**
 *  Shares logic for crawling domain.
 */
public interface CrawlerManager {

    /**
     * Returns all detected {@link URL}s placed in the {@code url} domain and its sub-domains.
     *
     * @param url of the domain for lookup.
     * @return Unique set of urls or empty set if any link didn't be detected.
     */
    Page listAllDetectedUrls(URL url) throws IOException;
}
