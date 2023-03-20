package io.supercheetos.blogsearchservice.blog;

import io.supercheetos.blogsearchservice.CommonDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogDto;
import io.supercheetos.blogsearchservice.blogsearch.BlogSort;
import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:test-data/clean-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BlogControllerTest {
    public static final String URL_BLOG_SEARCH = "/v1/blog/documents/search";

    @Autowired
    private TestRestTemplate testRest;
    @MockBean(name = "kakaoSearchClient")
    private SearchClient searchClient;
    @MockBean(name = "naverSearchClient")
    private SearchClient fallbackSearchClient;

    @Test
    @DisplayName("검색 결과가 없는 경우, 성공 응답하고 contents은 빈 리스트를 반환한다.")
    void testEmptyBlogDocuments() throws Exception {
        var query = "프로그래밍";
        when(searchClient.search(eq(query), anyInt(), anyInt(), any()))
                .thenReturn(new CommonDto.Page<>(Collections.emptyList(), 1, 10, BlogSort.RECENCY.getValue(), 10));

        var resp = testRest.getForEntity(URL_BLOG_SEARCH + "?query={test}", String.class, query);

        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": true,
                        "resultCode": 0,
                        "resultMessage": ""
                    },
                    "documents": {
                        "contents": [],
                        "page": 1,
                        "size": 10,
                        "sort": "recency",
                        "totalCount": 10
                    }
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("검색 결과가 있는 경우, 성공 응답하고 contents에 검색 결과과 담겨있어야 한다.")
    void testBlogDocumentsSearch() throws Exception {
        var query = "프로그래밍";
        var docs = List.of(
                new BlogDto.Document("title1", "contents1", "url1", "blogName1", ZonedDateTime.parse("2023-03-19T14:00:00Z"))
        );
        when(searchClient.search(eq(query), anyInt(), anyInt(), any()))
                .then(inv -> new CommonDto.Page<>(
                        docs,
                        inv.getArgument(1, Integer.class),
                        inv.getArgument(2, Integer.class),
                        inv.getArgument(3, BlogSort.class).getValue(),
                        100
                ));

        var resp = testRest.getForEntity(URL_BLOG_SEARCH + "?query={query}&page=5&size=1&sort=RECENCY", String.class, query);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": true,
                        "resultCode": 0,
                        "resultMessage": ""
                    },
                    "documents": {
                        "contents": [
                            {
                                "title": "title1",
                                "contents": "contents1",
                                "url": "url1",
                                "blogName": "blogName1",
                                "datetime": "2023-03-19T14:00:00Z"
                            }
                        ],
                        "page": 5,
                        "size": 1,
                        "sort": "recency",
                        "totalCount": 100
                    }
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("쿼리 파라미터는 기본값을 사용하고 검색 결과가 있는 경우, 성공 응답하고 contents에 검색 결과과 담겨있어야 한다.")
    void testBlogDocumentsSearchWithDefaultParams() throws Exception {
        var query = "프로그래밍";
        var docs = List.of(
                new BlogDto.Document("title1", "contents1", "url1", "blogName1", ZonedDateTime.parse("2023-03-19T14:00:00Z"))
        );
        when(searchClient.search(eq(query), anyInt(), anyInt(), any()))
                .then(inv -> new CommonDto.Page<>(
                        docs,
                        inv.getArgument(1, Integer.class),
                        inv.getArgument(2, Integer.class),
                        inv.getArgument(3, BlogSort.class).getValue(),
                        100
                ));

        var resp = testRest.getForEntity(URL_BLOG_SEARCH + "?query={query}", String.class, query);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": true,
                        "resultCode": 0,
                        "resultMessage": ""
                    },
                    "documents": {
                        "contents": [
                            {
                                "title": "title1",
                                "contents": "contents1",
                                "url": "url1",
                                "blogName": "blogName1",
                                "datetime": "2023-03-19T14:00:00Z"
                            }
                        ],
                        "page": 1,
                        "size": 10,
                        "sort": "accuracy",
                        "totalCount": 100
                    }
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("query 쿼리 파라미터를 지정하지 않은 경우, query 파라미터가 필요하다는 에러 응답을 한다.")
    void testSearchBlogDocuments_noQueryUrlQueryParameter() throws Exception {
        var resp = testRest.getForEntity(URL_BLOG_SEARCH, String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": false,
                        "resultCode": -3,
                        "resultMessage": "Query parameter 'query' required."
                    }
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("쿼리 파라미터가 잘못된 경우, 잘못된 쿼리 파라미터에 대한 에러 응답을 한다.")
    void testSearchBlogDocuments_emptyQueryUrlQueryParameter() throws Exception {
        var resp = testRest.getForEntity(URL_BLOG_SEARCH + "?query=", String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": false,
                        "resultCode": -3,
                        "resultMessage": "query: 비어 있을 수 없습니다"
                    }
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("query에 공백이 포함된 경우, 성공 응답해야 한다.")
    void testSearchBlogDocuments_spaceQueryUrlQueryParameter() throws Exception {
        var query = "hello world";
        when(searchClient.search(eq(query), anyInt(), anyInt(), any()))
                .thenReturn(new CommonDto.Page<>(List.of(), 1, 10, "sort", 100));

        var resp = testRest.getForEntity(URL_BLOG_SEARCH + "?query={query}", String.class, query);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("""
                {
                    "header":{"isSuccessful":true,"resultCode":0,"resultMessage":""},
                    "documents":{"contents":[],"page":1,"size":10,"sort":"sort","totalCount":100}
                }""",
                resp.getBody(),
                false
        );
    }
}
