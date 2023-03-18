package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

import io.supercheetos.blogsearchservice.blogsearch.BlogDocument;
import io.supercheetos.blogsearchservice.blogsearch.searcher.BlogSearcher;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class KakaoBlogSearcher implements BlogSearcher {
    private final RestTemplate restTemplate;

    @Override
    public Page<BlogDocument> search(String query, Pageable pageable, BlogSort sort) {
        var response = restTemplate.getForObject(
                "/v2/search/blog?query={query}&sort={sort}&page={page}&size={size}",
                KakaoResponses.SearchResult.class,
                query, sort.getValue(), pageable.getPageNumber(), pageable.getPageSize()
        );

        var docs = response.documents()
                .stream()
                .map(doc -> new BlogDocument(doc.blogname(), doc.title(), doc.contents()))
                .toList();
        var meta = response.meta();
        return new PageImpl<>(docs, pageable, meta.totalCount());
    }
}
