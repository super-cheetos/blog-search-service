package io.supercheetos.blogsearchservice.blog.searcher.naver;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public final class NaverDto {
    public static final String TIMEZONE = "Asia/Seoul";

    private NaverDto() {}

    public record Document(
            String title,
            String link,
            String description,
            String bloggername,
            String bloggerlink,
            @JsonFormat(pattern = "yyyyMMdd", timezone = TIMEZONE)
            LocalDate postdate
    ) {
    }

    public record SearchResponse(
            Integer total,
            Integer start,
            Integer display,
            List<Document> items
    ) {
    }
}
