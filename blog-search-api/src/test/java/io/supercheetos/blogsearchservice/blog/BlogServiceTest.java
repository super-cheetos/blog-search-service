package io.supercheetos.blogsearchservice.blog;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blog.searcher.SearchClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class BlogServiceTest {
    private SearchClient searchClient;
    private SearchClient fallbackSearchClient;
    private ApplicationEventPublisher publisher;
    private CircuitBreakerFactory<?, ?> cbFactory;

    private BlogService blogService;

    @BeforeEach
    void setup() {
        this.searchClient = mock(SearchClient.class);
        this.fallbackSearchClient = mock(SearchClient.class);
        this.publisher = mock(ApplicationEventPublisher.class);
        this.cbFactory = new Resilience4JCircuitBreakerFactory(
                CircuitBreakerRegistry.ofDefaults(),
                TimeLimiterRegistry.ofDefaults(),
                null
        );
        this.blogService = new BlogService(searchClient, fallbackSearchClient, publisher, cbFactory);
    }

    @Test
    @DisplayName("일반적인 성공 케이스, searchClient의 search를 호출")
    void testSearch() {
        var query = "hello";
        var page = 1;
        var size = 100;
        var sort = BlogSort.getDefault();
        var expected = new CommonDto.Page<BlogDto.Document>(List.of(), page, size, sort.getValue(), 1000);
        when(searchClient.search(any(), anyInt(), anyInt(), any()))
                .thenReturn(expected);

        var actual = blogService.search(query, page, size, sort);

        assertThat(actual).isEqualTo(expected);
        verify(searchClient, times(1)).search(eq(query), eq(page), eq(size), eq(sort));
    }

    @Test
    @DisplayName("searchClient가 실패하면 fallbackSearchClient가 호출되야 한다.")
    void testSearch_fallback() {
        var query = "hello2";
        var page = 1;
        var size = 100;
        var sort = BlogSort.getDefault();
        var expected = new CommonDto.Page<BlogDto.Document>(List.of(), page, size, sort.getValue(), 1000);
        when(searchClient.search(any(), anyInt(), anyInt(), any()))
                .thenThrow(new IllegalStateException());
        when(fallbackSearchClient.search(any(), anyInt(), anyInt(), any()))
                .thenReturn(expected);

        var actual = blogService.search(query, page, size, sort);

        assertThat(actual).isEqualTo(expected);
        verify(fallbackSearchClient, times(1)).search(eq(query), eq(page), eq(size), eq(sort));
    }

    @Test
    @DisplayName("모든 searchClient가 실패하는 경우, 예외 발생")
    void testSearch_failAll() {
        when(searchClient.search(any(), anyInt(), anyInt(), any()))
                .thenThrow(new RuntimeException("unknown fail"));
        when(fallbackSearchClient.search(any(), anyInt(), anyInt(), any()))
                .thenThrow(new SearchException("failed"));

        assertThatThrownBy(() -> blogService.search("hello3", 10, 100, BlogSort.ACCURACY))
                .isInstanceOf(SearchException.class);
    }
}
