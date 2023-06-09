package io.supercheetos.blogsearchservice.blog;

public enum BlogSort {
    ACCURACY("accuracy"), RECENCY("recency");

    private final String value;

    BlogSort(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BlogSort getDefault() {
        return ACCURACY;
    }
}
