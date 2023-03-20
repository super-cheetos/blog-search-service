package io.supercheetos.blogsearchservice.blog.searcher.naver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(NaverProperties.class)
@ConditionalOnProperty(name = "blog-search.naver.enabled", havingValue = "true")
public class NaverSearchConfiguration {
    @Bean
    @Order(2)
    public NaverSearchClient naverSearchClient(RestTemplateBuilder builder, NaverProperties props) {
        var restTemplate = builder.rootUri(props.baseUrl())
                .defaultHeader("X-Naver-Client-Id", props.clientId())
                .defaultHeader("X-Naver-Client-Secret", props.clientSecret())
                .build();
        return new NaverSearchClient(restTemplate);
    }
}
