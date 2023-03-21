package io.supercheetos.blogsearchservice.blog.searcher.naver;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "blog-search.naver")
@Validated
public record NaverProperties(
        Boolean enabled,
        @NotBlank
        String clientId,
        @NotBlank
        String clientSecret,
        @NotBlank
        String baseUrl
) {
}
