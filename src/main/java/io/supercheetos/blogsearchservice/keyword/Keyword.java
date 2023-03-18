package io.supercheetos.blogsearchservice.keyword;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "keyword", indexes = @Index(name = "idx_keyword_count", columnList = "count DESC"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Keyword {
    @Id
    private String name;
    
    @Column(name = "count", nullable = false)
    private int count;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Keyword(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public void increment() {
        this.count++;
    }
}
