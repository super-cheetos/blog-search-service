package io.supercheetos.blogsearchservice.blogsearch.searcher;

import io.supercheetos.blogsearchservice.blogsearch.SearchException;

public class InvalidSearchResultException extends SearchException {
    public InvalidSearchResultException(String message) {
        super(message);
    }
}
