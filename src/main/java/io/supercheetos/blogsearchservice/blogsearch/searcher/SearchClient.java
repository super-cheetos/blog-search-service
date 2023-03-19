package io.supercheetos.blogsearchservice.blogsearch.searcher;

import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;

public interface SearchClient {
    /**
     * @param query 검색 키워드
     * @param page 검색할 페이지. 1부터 시작
     * @param size 검색할 블로그 글 개수
     * @param sort 정렬 방식
     * @return 검색된 블로그 글 목록
     */
    CommonDto.Page<BlogDto.Document> search(String query, int page, int size, BlogSort sort);
}
