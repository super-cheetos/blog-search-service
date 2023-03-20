package io.supercheetos.blogsearchservice.blog.searcher.naver;

import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blog.BlogDto;
import io.supercheetos.blogsearchservice.blog.BlogSort;
import io.supercheetos.blogsearchservice.blog.SearchException;
import io.supercheetos.blogsearchservice.blog.searcher.InvalidSearchResultException;
import io.supercheetos.blogsearchservice.blog.searcher.SearchClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

@Slf4j
@AllArgsConstructor
public class NaverSearchClient implements SearchClient {
    private final RestTemplate restTemplate;

    @Override
    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        log.debug("Called NaverSearchClient.search. : query={}, page={}, size={}, sort={}", query, page, size, sort);
        NaverDto.SearchResponse response;
        try {
            response = restTemplate.getForObject(
                    "/v1/search/blog.json?query={query}&start={start}&display={display}&sort={sort}",
                    NaverDto.SearchResponse.class,
                    query,
                    (page - 1) * size + 1,
                    size,
                    toNaverSort(sort)
            );
        } catch (Exception e) {
            throw new SearchException("Failed to get blog documents from Naver.", e);
        }

        if (response == null) {
            log.warn("Response body of Naver REST API must not be null. : query={}, page={}, size={}, sort={}", query, page, size, sort);
            throw new InvalidSearchResultException("Response body of Naver REST API must not be null.");
        }
        if (CollectionUtils.isEmpty(response.items())) {
            return new CommonDto.Page<>(Collections.emptyList(), page, size, sort.getValue(), response.total());
        }

        var docs = response.items()
                .stream()
                .map(doc -> new BlogDto.Document(
                        doc.title(),
                        doc.description(),
                        doc.link(),
                        doc.bloggername(),
                        ZonedDateTime.of(doc.postdate(), LocalTime.MIN, ZoneId.of(NaverDto.TIMEZONE))
                ))
                .toList();
        return new CommonDto.Page<>(docs, page, size, sort.getValue(), response.total());
    }

    private String toNaverSort(BlogSort blogSort) {
        return switch (blogSort) {
            case RECENCY -> "date";
            default -> "sim";
        };
    }
}
