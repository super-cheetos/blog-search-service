package io.supercheetos.blogsearchservice.blogsearch;

import io.supercheetos.blogsearchservice.CommonDto;

import java.time.ZonedDateTime;

public final class BlogDto {
    private BlogDto() {
    }

    public record Document(
            String title,
            String contents,
            String url,
            String blogName,
            ZonedDateTime datetime
    ) {
    }

    public record SearchResponse(
            CommonDto.Header header,
            CommonDto.Page<Document> documents
    ) {
        public static SearchResponse ok(CommonDto.Page<Document> documents) {
            return new SearchResponse(CommonDto.Header.OK, documents);
        }
    }
}
