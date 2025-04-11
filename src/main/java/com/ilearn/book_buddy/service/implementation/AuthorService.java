package com.ilearn.book_buddy.service.implementation;

import com.ilearn.book_buddy.data.entity.Author;
import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.helpers.BeanUtilHelper;
import com.ilearn.book_buddy.repository.AuthorRepository;
import com.ilearn.book_buddy.rest.request.AuthorRequest;
import com.ilearn.book_buddy.service.interfaces.AuthorUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static com.ilearn.book_buddy.constants.ErrorMessages.ALREADY_EXIST;
import static com.ilearn.book_buddy.constants.ErrorMessages.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService implements AuthorUseCase {

    private final AuthorRepository authorRepository;


    @Override
    public Author createAuthor(AuthorRequest authorRequest) {
        authorDoesNotExist(authorRequest.getEmail());
        Author author = new Author();
        BeanUtils.copyProperties(authorRequest, author);
        return authorRepository.save(author);
    }

    public void save(Author author){
        authorRepository.save(author);
    }

    public Optional<Author> findByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public void updateAuthor(String id, AuthorRequest updateAuthorRequest) throws InvocationTargetException, IllegalAccessException {
        Author author = this.findAuthor(id);
        BeanUtilHelper.copyPropertiesIgnoreNull(updateAuthorRequest, author);
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(String id) {
        Author author = this.findAuthor(id);

        if (author != null) {
            // Remove the author's association from books
            for (Book book : author.getBooks()) {
                book.getAuthors().remove(author);
            }
            // Clear the author's association from books
            author.getBooks().clear();
            authorRepository.delete(author);
        }
    }

    private Author findAuthor(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void authorDoesNotExist(String email) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(email);
        if(optionalAuthor.isPresent()){
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
    }

}
