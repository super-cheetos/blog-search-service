package io.supercheetos.blogsearchservice.blog.searcher.kakao;

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

import java.util.Collections;

@Slf4j
@AllArgsConstructor
public class KakaoSearchClient implements SearchClient {
    private final RestTemplate restTemplate;

    @Override
    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        log.debug("Called KakaoSearchClient.search. : query={}, page={}, size={}, sort={}", query, page, size, sort);
        KakaoDto.SearchResponse response;
        try {
            response = restTemplate.getForObject(
                    "/v2/search/blog?query={query}&sort={sort}&page={page}&size={size}",
                    KakaoDto.SearchResponse.class,
                    query, sort.getValue(), page, size
            );
        } catch (Exception e) {
            throw new SearchException("Failed to get blog documents from Kakao.", e);
        }

        if (response == null) {
            log.warn("Response body of Kakao REST API must not be null. : query={}, page={}, size={}, sort={}", query, page, size, sort);
            throw new InvalidSearchResultException("Response body of Kakao REST API must not be null.");
        }
        var meta = response.meta();
        if (meta == null) {
            log.warn("Kakao meta field required. : query={}, page={}, size={}, sort={}", query, page, size, sort);
            throw new InvalidSearchResultException("Kakao meta field required.");
        }
        var respDocs = response.documents();
        if (CollectionUtils.isEmpty(respDocs)) {
            return new CommonDto.Page<>(Collections.emptyList(), page, size, sort.getValue(), meta.totalCount());
        }

        var docs = response.documents()
                .stream()
                .map(doc -> new BlogDto.Document(
                        doc.title(),
                        doc.contents(),
                        doc.url(),
                        doc.blogname(),
                        doc.datetime()
                ))
                .toList();
        return new CommonDto.Page<>(docs, page, size, sort.getValue(), meta.totalCount());
    }
}
