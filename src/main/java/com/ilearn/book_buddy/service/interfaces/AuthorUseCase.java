package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.data.entity.Author;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface AuthorUseCase {
    Author createAuthor(AuthorRequest author);
    void updateAuthor(long id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException;
    void deleteAuthor(long id);
    Optional<Author> findByEmail(String email);
    Optional<Author> findById(long id);
}
