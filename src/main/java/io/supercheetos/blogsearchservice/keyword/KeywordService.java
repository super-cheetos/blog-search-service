package io.supercheetos.blogsearchservice.keyword;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    @Transactional(readOnly = true)
    public List<Responses.Keyword> findTop10() {
        var sort = Sort.by("count").descending();
        var pageable = PageRequest.of(0, 10, sort);
        var keywords = keywordRepository.findAll(pageable);
        return keywords.stream()
                .map(entity -> new Responses.Keyword(entity.getName(), entity.getCount()))
                .toList();
    }
}
