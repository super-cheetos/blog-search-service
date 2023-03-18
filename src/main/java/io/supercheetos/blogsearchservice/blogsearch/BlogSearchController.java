package io.supercheetos.blogsearchservice.blogsearch;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/v1/blog/documents")
public class BlogSearchController {
    private final BlogSearchService service;

    @GetMapping
    public Page<BlogDocument> get(@RequestParam String query, Pageable pageable) {
        return service.search(query, pageable.getPageNumber(), pageable.getPageSize(), BlogSort.getDefault());
    }
}