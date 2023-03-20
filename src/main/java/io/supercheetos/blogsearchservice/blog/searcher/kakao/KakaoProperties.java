package io.supercheetos.blogsearchservice.blog.searcher.kakao;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@ConfigurationProperties(prefix = "blog-search.kakao")
@Validated
public record KakaoProperties(
        Boolean enabled,
        @NotBlank
        String accessToken,
        @NotBlank
        String baseUrl
) {
}
