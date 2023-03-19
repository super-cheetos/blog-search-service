package io.supercheetos.blogsearchservice.keyword;

import io.supercheetos.blogsearchservice.blogsearch.searcher.SearchClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:test-data/save-keywords.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data/clean-keywords.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class KeywordControllerTest {
    @Autowired
    private TestRestTemplate testRest;
    @MockBean
    private SearchClient searchClient;

    @Test
    void testGetTop10() {
        var resp = testRest.getForEntity("/v1/keywords/top10", String.class);

        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isEqualTo("""
                {"header":{"isSuccessful":true,"resultCode":0,"resultMessage":""},"top10":[{"name":"Somewhere","count":2000},{"name":"프로그래밍","count":1500},{"name":"자바","count":1400},{"name":"한글","count":1300},{"name":"영어","count":1200},{"name":"일본어","count":1100},{"name":"hello","count":1000},{"name":"World","count":1000},{"name":"apple","count":900},{"name":"커피","count":800}]}""");
    }
}