package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import io.supercheetos.blogsearchservice.keyword.KeywordQueryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BlogService {
    private final SearchClient searchClient;
    private final SearchClient fallbackSearchClient;
    private final ApplicationEventPublisher publisher;
    private final CircuitBreakerFactory<?, ?> cbFactory;

    public BlogService(
            @Qualifier("kakaoSearchClient") SearchClient searchClient,
            @Qualifier("naverSearchClient") SearchClient fallbackSearchClient,
            ApplicationEventPublisher publisher,
            CircuitBreakerFactory<?, ?> cbFactory
    ) {
        this.searchClient = searchClient;
        this.fallbackSearchClient = fallbackSearchClient;
        this.publisher = publisher;
        this.cbFactory = cbFactory;
    }

    public CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort) {
        publisher.publishEvent(new KeywordQueryEvent(query));

        return cbFactory.create("searchBlogDocuments").run(
                () -> searchClient.search(query, page, size, sort),
                e -> {
                    log.warn("Main SearchClient failed. Fallback SearchClient called. : query={}, page={}, size={}, sort={}", query, page, size, sort, e);
                    return fallbackSearchClient.search(query, page, size, sort);
                }
        );
    }
}
