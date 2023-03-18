package io.supercheetos.blogsearchservice.blogsearch;

public enum BlogSort {
    ACCURACY("accuracy"), RECENCY("recency");

    private String value;

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
