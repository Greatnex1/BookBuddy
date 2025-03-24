package com.ilearn.book_buddy.repository;

import com.ilearn.book_buddy.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    Optional<Book> findBookByIsbn(int isbn);

    boolean existsByIsbn(int isbn);

}
