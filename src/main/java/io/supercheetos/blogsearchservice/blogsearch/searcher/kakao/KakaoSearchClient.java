package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

import io.supercheetos.blogsearchservice.blogsearch.BlogDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;
import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import io.supercheetos.blogsearchservice.common.CommonDto;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
public class KakaoSearchClient implements SearchClient {
    private final RestTemplate restTemplate;

    @Override
    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        var response = restTemplate.getForObject(
                "/v2/search/blog?query={query}&sort={sort}&page={page}&size={size}",
                KakaoDto.SearchResult.class,
                query, sort.getValue(), page, size
        );

        var docs = response.documents()
                .stream()
                .map(doc -> new BlogDto.Document(
                        doc.title(),
                        doc.contents(),
                        doc.url(),
                        doc.blogname(),
                        doc.thumbnail(),
                        doc.datetime()
                ))
                .toList();
        var meta = response.meta();
        return new CommonDto.Page<>(docs, page, size, sort.getValue(), meta.totalCount());
    }
}
