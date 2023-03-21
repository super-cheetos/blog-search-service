package io.supercheetos.blogsearchservice.blog.searcher.kakao;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;


@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
@ConditionalOnProperty(name = "blog-search.kakao.enabled", havingValue = "true")
public class KakaoSearchConfiguration {
    @Bean
    @Order(1)
    public KakaoSearchClient kakaoSearchClient(
            RestTemplateBuilder builder,
            KakaoProperties props
    ) {
        var restTemplate = builder.rootUri(props.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + props.accessToken())
                .build();
        return new KakaoSearchClient(restTemplate);
    }
}
