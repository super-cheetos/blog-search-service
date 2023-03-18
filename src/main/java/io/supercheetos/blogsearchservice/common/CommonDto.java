package io.supercheetos.blogsearchservice.common;

import java.util.List;

public class CommonDto {
    public record Page<T>(
            List<T> contents,
            int page,
            int size,
            String sort,
            long totalCount
    ) {
    }

    public record Header(
            boolean isSuccessful,
            int resultCode,
            String resultMessage
    ) {
        public static final Header OK = new Header(true, 0, "");
    }
}
