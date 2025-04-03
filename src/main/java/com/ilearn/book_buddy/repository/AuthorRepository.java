package com.ilearn.book_buddy.repository;

import com.ilearn.book_buddy.data.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, String> {
    Optional<Author> findByEmail(String email);
}
