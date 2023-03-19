package io.supercheetos.blogsearchservice.blogsearch.searcher.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blog-search.naver")
public record NaverProperties(
        Boolean enabled,
        String clientId,
        String clientSecret,
        String baseUrl
) {
}
