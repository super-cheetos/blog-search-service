package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.blogsearch.searcher.kakao.KakaoBlogSearcher;
import io.supercheetos.blogsearchservice.keyword.Keyword;
import io.supercheetos.blogsearchservice.keyword.KeywordRepository;
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
    private final KeywordRepository keywordRepository;
    private final KakaoBlogSearcher kakaoBlogSearcher;

    @Transactional
    public Page<BlogDocument> search(String query, int page, int size, BlogSort sort) {
        for (var word : query.split(" ")) {
            log.debug("word={}", word);
            var keyword = keywordRepository.findById(word)
                    .orElseGet(() -> new Keyword(word, 0));
            keyword.increment();
            keywordRepository.save(keyword);
        }
        return kakaoBlogSearcher.search(query, PageRequest.of(page, size), sort);
    }

}
