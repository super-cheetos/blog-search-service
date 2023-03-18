package io.supercheetos.blogsearchservice;

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
        public static final Header UNKNOWN_ERROR = new Header(false, -9999, "Unknown error");
    }

    public record NoDataResponse(
            Header header
    ) {
        public static NoDataResponse error(int resultCode, String resultMessage) {
            return new NoDataResponse(new CommonDto.Header(false, resultCode, resultMessage));
        }
    }
}
