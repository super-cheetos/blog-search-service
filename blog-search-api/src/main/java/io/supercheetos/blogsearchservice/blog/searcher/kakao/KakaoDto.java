package io.supercheetos.blogsearchservice.blog.searcher.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class KakaoDto {
    public record Document(
            String title,
            String contents,
            String url,
            String blogname,
            String thumbnail,
            ZonedDateTime datetime
    ) {
    }

    public record PageMeta(
            @JsonProperty("total_count")
            Integer totalCount,
            @JsonProperty("pageable_count")
            Integer pageableCount,
            @JsonProperty("is_end")
            Boolean isEnd
    ) {
    }

    public record SearchResponse(
            PageMeta meta,
            List<Document> documents
    ) {
    }
}
