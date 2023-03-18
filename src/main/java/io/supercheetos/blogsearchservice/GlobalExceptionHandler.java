package io.supercheetos.blogsearchservice;

import io.supercheetos.blogsearchservice.blogsearch.SearchException;
import io.supercheetos.blogsearchservice.blogsearch.searcher.InvalidSearchResultException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonDto.NoDataResponse> handleConstraintViolationException(ConstraintViolationException e) {
        var message = e.getConstraintViolations()
                .stream()
                .map(cv -> {
                    var pathIter = cv.getPropertyPath().iterator();
                    Path.Node node = pathIter.next();
                    while (pathIter.hasNext()) {
                        node = pathIter.next();
                    }
                    return node.getName() + ": " + cv.getMessage();
                })
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(CommonDto.NoDataResponse.error(-3, message));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CommonDto.NoDataResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        var message = String.format("Query parameter '%s' required.", e.getParameterName());
        return ResponseEntity.badRequest().body(CommonDto.NoDataResponse.error(-3, message));
    }

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
