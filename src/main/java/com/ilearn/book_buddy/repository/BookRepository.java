package com.ilearn.book_buddy.repository;

import com.ilearn.book_buddy.data.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, String>, JpaSpecificationExecutor<BookEntity> {
    Optional<BookEntity> findBookByIsbn(int isbn);

    boolean existsByIsbn(int isbn);

}
