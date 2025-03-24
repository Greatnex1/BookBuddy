package com.ilearn.book_buddy.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;

import static com.ilearn.book_buddy.handlers.messages.ErrorMessages.APPENDABLE_SEPARATOR;


@Getter
@Setter
@MappedSuperclass
public class AppendableReferenceEntity {
    @Id
    @Column(nullable = false)
    private String id;

    @Column(length = 20, nullable = false, updatable = false, unique = true)
    private String reference;

    @PrePersist
    public void appendReference() {
        if (!StringUtils.hasText(this.reference)) {
            this.reference = RandomStringUtils.randomAlphanumeric(6, 10);
        }
    }

    public String getReference() {
        return String.format("%s%s%s", this.id, APPENDABLE_SEPARATOR, this.reference);
    }

}
