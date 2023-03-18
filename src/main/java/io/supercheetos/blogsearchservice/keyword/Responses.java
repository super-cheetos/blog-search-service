package io.supercheetos.blogsearchservice.keyword;

public final class Responses {
    private Responses() {}

    public record Keyword(String name, int count) {
    }
}
