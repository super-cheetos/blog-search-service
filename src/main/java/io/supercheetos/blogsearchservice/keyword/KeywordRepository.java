package io.supercheetos.blogsearchservice.keyword;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, String> {   
}
