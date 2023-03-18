package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
@EnableConfigurationProperties(KakaoBlogSearcherProperties.class)
public class KakaoBlogSearcherConfiguration {
    @Bean
    public KakaoBlogSearcher kakaoBlogSearcher(
            RestTemplateBuilder builder,
            KakaoBlogSearcherProperties props
    ) {
        var restTemplate = builder.rootUri(props.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK " + props.accessToken())
                .build();
        return new KakaoBlogSearcher(restTemplate);
    }
}
