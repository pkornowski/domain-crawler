package com.domaincrawler.crawler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RestController("/")
public class DomainController {

    @PostMapping("/urls")
    public ResponseEntity<Set<String>> retrieveUrlsFromPage(@RequestBody String stringUrl) throws IOException {

        return new ResponseEntity<>(new HashSet<>(), HttpStatus.OK);
    }
}
