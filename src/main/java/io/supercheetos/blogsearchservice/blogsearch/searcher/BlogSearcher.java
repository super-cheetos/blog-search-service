package io.supercheetos.blogsearchservice.blogsearch.searcher;

import io.supercheetos.blogsearchservice.blogsearch.BlogDocument;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogSearcher {
    Page<BlogDocument> search(String query, Pageable pageable, BlogSort sort);
}
