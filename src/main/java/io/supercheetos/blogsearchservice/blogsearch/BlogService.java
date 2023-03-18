package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import io.supercheetos.blogsearchservice.blogsearch.searcher.kakao.KakaoSearchClient;
import io.supercheetos.blogsearchservice.keyword.KeywordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BlogService {
    private final KeywordService keywordService;
    private final SearchClient searchClient;

    @Transactional
    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        for (var keywordName : query.split(" +")) {
            keywordService.increment(keywordName);
        }
        return searchClient.search(query, page, size, sort);
    }
}
