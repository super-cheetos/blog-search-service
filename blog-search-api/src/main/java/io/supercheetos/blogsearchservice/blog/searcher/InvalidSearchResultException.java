package io.supercheetos.blogsearchservice.blog.searcher;

import io.supercheetos.blogsearchservice.blog.SearchException;

public class InvalidSearchResultException extends SearchException {
    public InvalidSearchResultException(String message) {
        super(message);
    }
}
