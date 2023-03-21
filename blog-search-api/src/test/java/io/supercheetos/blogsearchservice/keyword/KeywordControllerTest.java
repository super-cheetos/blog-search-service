package io.supercheetos.blogsearchservice.keyword;

import io.supercheetos.blogsearchservice.blog.searcher.SearchClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KeywordControllerTest {
    private static final String URL_TOP10 = "/v1/keywords/top10";

    @Autowired
    private TestRestTemplate testRest;
    @MockBean(name = "kakaoSearchClient")
    private SearchClient searchClient;
    @MockBean(name = "naverSearchClient")
    private SearchClient fallbackSearchClient;

    @Test
    @DisplayName("가장 많이 검색된 키워드 Top10을 반환한다.")
    @Sql(scripts = "classpath:test-data/save-keywords.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:test-data/clean-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetTop10() throws Exception {
        var resp = testRest.getForEntity(URL_TOP10, String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": true,
                        "resultCode": 0,
                        "resultMessage": ""
                    },
                    "top10": [ 
                        {
                            "name": "Somewhere",
                            "count": 2000
                        },
                        {
                            "name": "프로그래밍",
                            "count": 1500
                        },
                        {
                            "name": "자바",
                            "count": 1400
                        },
                        {
                            "name": "한글",
                            "count": 1300
                        },
                        {
                            "name": "영어",
                            "count": 1200
                        },
                        {
                            "name": "일본어",
                            "count": 1100
                        },
                        {
                            "name": "hello",
                            "count": 1000
                        },
                        {
                            "name": "World",
                            "count": 1000
                        },
                        {
                            "name": "apple",
                            "count": 900
                        },
                        {
                            "name": "커피",
                            "count": 800
                        }
                    ]
                }""",
                resp.getBody(),
                false
        );
    }

    @Test
    @DisplayName("블로그 검색 이후, 키워드 Top10 조회")
    @Sql(scripts = "classpath:test-data/clean-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testGetTop10_afterBlogSearch() throws Exception {
        var searchUrl = "/v1/blog/documents/search?query={query}";
        testRest.getForEntity(searchUrl, String.class, "hello1");
        testRest.getForEntity(searchUrl, String.class, "hello2");
        testRest.getForEntity(searchUrl, String.class, "hello1");

        var resp = testRest.getForEntity(URL_TOP10, String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONAssert.assertEquals("""
                {
                    "header": {
                        "isSuccessful": true,
                        "resultCode": 0,
                        "resultMessage": ""
                    },
                    "top10": [
                        {
                            "name": "hello1",
                            "count": 2
                        },
                        {
                            "name": "hello2",
                            "count": 1
                        }
                    ]
                }
                """,
                resp.getBody(),
                false
        );
    }
}