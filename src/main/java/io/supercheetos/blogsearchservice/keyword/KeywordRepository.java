package io.supercheetos.blogsearchservice.keyword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface KeywordRepository extends JpaRepository<Keyword, String> {
    @Modifying
    @Query("UPDATE Keyword k SET k.count = k.count + 1 WHERE k.name = :name")
    void increment(String name);
}
