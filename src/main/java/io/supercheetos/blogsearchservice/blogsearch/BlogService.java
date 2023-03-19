package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.keyword.KeywordQueryEvent;
import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BlogService {
    private final List<SearchClient> searchClients;
    private final ApplicationEventPublisher publisher;

    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        publisher.publishEvent(new KeywordQueryEvent(query));

        RuntimeException lastException = new IllegalStateException("SearchClient list required");
        for (var searchClient : searchClients) {
            try {
                return searchClient.search(query, page, size, sort);
            } catch (RuntimeException e) {
                log.warn("Exception occurred.", e);
                lastException = e;
            }
        }
        throw lastException;
    }
}
