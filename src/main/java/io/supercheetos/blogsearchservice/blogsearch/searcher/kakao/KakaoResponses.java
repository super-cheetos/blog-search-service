package io.supercheetos.blogsearchservice.blogsearch.searcher.kakao;

import java.time.ZonedDateTime;
import java.util.List;

public class KakaoResponses {
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
            int totalCount,
            int pageableCount,
            boolean isEnd
    ) {
    }

    public record SearchResult(
            PageMeta meta,
            List<Document> documents
    ) {
    }
}
