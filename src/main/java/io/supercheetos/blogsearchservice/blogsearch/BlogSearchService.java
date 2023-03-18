package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.blogsearch.searcher.kakao.KakaoBlogSearcher;
import io.supercheetos.blogsearchservice.keyword.Keyword;
import io.supercheetos.blogsearchservice.keyword.KeywordRepository;
import io.supercheetos.blogsearchservice.keyword.KeywordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class BlogSearchService {
    private final KeywordService keywordService;
    private final KakaoBlogSearcher kakaoBlogSearcher;

    @Transactional
    public Page<BlogDocument> search(String query, int page, int size, BlogSort sort) {
        for (var keywordName : query.split(" +")) {
            keywordService.increment(keywordName);
        }
        return kakaoBlogSearcher.search(query, PageRequest.of(page, size), sort);
    }

}
