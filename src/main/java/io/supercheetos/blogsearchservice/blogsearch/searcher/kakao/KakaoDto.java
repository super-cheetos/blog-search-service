package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

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
            int totalCount,
            @JsonProperty("pageable_count")
            int pageableCount,
            @JsonProperty("is_end")
            boolean isEnd
    ) {
    }

    public record SearchResult(
            PageMeta meta,
            List<Document> documents
    ) {
    }
}
