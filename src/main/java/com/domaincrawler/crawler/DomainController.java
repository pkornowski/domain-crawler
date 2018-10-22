package com.domaincrawler.crawler;

import com.domaincrawler.crawler.manager.CrawlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@RestController("/")
public class DomainController {

    private CrawlerManager crawlerManager;

    @Autowired
    public DomainController(final CrawlerManager crawlerManager) {
        this.crawlerManager = crawlerManager;
    }

    @PostMapping("/urls")
    public ResponseEntity<Set<String>> retrieveUrlsFromPage(@RequestBody String stringUrl) throws IOException {
        final HashSet<String> detectedUrls = crawlerManager.listAllDetectedUrls(new URL(stringUrl));

        return new ResponseEntity<>(detectedUrls, HttpStatus.OK);
    }
}
