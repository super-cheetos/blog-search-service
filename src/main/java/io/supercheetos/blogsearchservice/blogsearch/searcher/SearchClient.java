package io.supercheetos.blogsearchservice.blogsearch.searcher;

import io.supercheetos.blogsearchservice.blogsearch.BlogDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;
import io.supercheetos.blogsearchservice.common.CommonDto;

public interface SearchClient {
    CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort);
}
