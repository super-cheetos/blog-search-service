package io.supercheetos.blogsearchservice;

import io.supercheetos.blogsearchservice.blogsearch.SearchException;
import io.supercheetos.blogsearchservice.blogsearch.searcher.InvalidSearchResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final ResponseEntity<CommonDto.NoDataResponse> INVALID_SEARCH_RESULT_EXCEPTION_RESPONSE =
            ResponseEntity.internalServerError()
                    .body(CommonDto.NoDataResponse.error(-2, "Failed to search data, document source is invalid."));
    private static final ResponseEntity<CommonDto.NoDataResponse> SEARCH_EXCEPTION_RESPONSE =
            ResponseEntity.internalServerError()
                    .body(CommonDto.NoDataResponse.error(-1, "failed to search data"));
    private static final ResponseEntity<CommonDto.NoDataResponse> EXCEPTION_RESPONSE =
            ResponseEntity.internalServerError()
                    .body(new CommonDto.NoDataResponse(CommonDto.Header.UNKNOWN_ERROR));

    @ExceptionHandler(InvalidSearchResultException.class)
    public ResponseEntity<CommonDto.NoDataResponse> handleInvalidSearchResultException() {
        return INVALID_SEARCH_RESULT_EXCEPTION_RESPONSE;
    }

    @ExceptionHandler(SearchException.class)
    public ResponseEntity<CommonDto.NoDataResponse> handleSearchException() {
        return SEARCH_EXCEPTION_RESPONSE;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonDto.NoDataResponse> handleException(Exception e) {
        log.error("Unexpected exception.", e);
        return EXCEPTION_RESPONSE;
    }
}
