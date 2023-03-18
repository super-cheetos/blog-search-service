package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "blog-search.kakao")
public record KakaoBlogSearcherProperties(String accessToken, String baseUrl) {
}