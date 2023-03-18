package io.supercheetos.blogsearchservice.blogsearch;

import lombok.AllArgsConstructor;
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

    @GetMapping("/search")
    public BlogDto.SearchResponse search(
            @RequestParam String query,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "ACCURACY") BlogSort sort
    ) {
        return BlogDto.SearchResponse.ok(service.search(query, page, size, sort));
    }
}