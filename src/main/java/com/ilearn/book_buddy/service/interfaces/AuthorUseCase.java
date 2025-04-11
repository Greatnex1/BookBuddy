package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.data.entity.Author;
import com.ilearn.book_buddy.rest.request.AuthorRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface AuthorUseCase {
    Author createAuthor(AuthorRequest author);
    void save(Author author);
    void updateAuthor(String id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException;
    void deleteAuthor(String id);
    Optional<Author> findByEmail(String email);
    Optional<Author> findById(String id);
}
