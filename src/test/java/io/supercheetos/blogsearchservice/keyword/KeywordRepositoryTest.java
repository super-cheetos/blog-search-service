package io.supercheetos.blogsearchservice.keyword;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class KeywordRepositoryTest {
    @Autowired
    private KeywordRepository repo;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testFindAll_top10() {
        var keywordTop10 = List.of("foo", "맥북", "world", "hello", "bar", "프로그래밍", "Java", "자바", "8", "0009");
        saveKeywords(Map.of(
                keywordTop10.get(2), 910,
                keywordTop10.get(9), 297,
                "other1", 100,
                keywordTop10.get(3), 500,
                keywordTop10.get(4), 489,
                keywordTop10.get(1), 999,
                keywordTop10.get(8), 299,
                keywordTop10.get(0), 1000,
                keywordTop10.get(7), 300,
                "other2", 54
        ));
        saveKeywords(Map.of(
                "other3", 56,
                keywordTop10.get(6), 399,
                keywordTop10.get(5), 413
        ));

        var sort = Sort.sort(Keyword.class).by(Keyword::getCount).descending();
        var result = repo.findAll(PageRequest.of(0, 10, sort));

        var names = result.stream()
                .map(Keyword::getName)
                .toList();
        assertThat(names).containsExactlyElementsOf(keywordTop10);
    }

    private void saveKeywords(Map<String, Integer> keywords) {
        keywords.forEach((name, count) -> testEntityManager.persist(new Keyword(name, count)));
    }
}