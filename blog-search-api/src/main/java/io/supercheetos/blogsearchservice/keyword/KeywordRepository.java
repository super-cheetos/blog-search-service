package io.supercheetos.blogsearchservice.keyword;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Keyword> findByName(String keywordName);
}
