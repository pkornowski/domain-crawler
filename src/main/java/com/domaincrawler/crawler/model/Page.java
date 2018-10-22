package com.domaincrawler.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;


@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Page implements Serializable {
    private String link;
    private HashSet<Page> childPages;
    private HashSet<String> resources;
    @JsonIgnore
    private boolean inDomain;
}
