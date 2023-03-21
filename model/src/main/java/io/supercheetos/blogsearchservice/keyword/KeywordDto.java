package io.supercheetos.blogsearchservice.keyword;

import io.supercheetos.blogsearchservice.CommonDto;

import java.util.List;

public final class KeywordDto {
    public record Keyword(
            String name,
            int count
    ) {
    }

    public record Top10Response(
            CommonDto.Header header,
            List<Keyword> top10
    ) {
        public static Top10Response ok(List<Keyword> top10) {
            return new Top10Response(CommonDto.Header.OK, top10);
        }
    }
}
