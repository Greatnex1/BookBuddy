package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.data.entity.BookEntity;
import com.ilearn.book_buddy.data.model.Book;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.rest.PageObject;
import com.ilearn.book_buddy.rest.request.BookRequest;
import com.ilearn.book_buddy.rest.response.PagedResponse;

import java.io.IOException;

public interface BookUseCase {
    Book registerBook(BookRequest book) throws IOException;
 Book getBook(String bookId) throws GenericException;
    void updateBook(long id, BookRequest bookRequest) throws IOException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    PageObject searchBookCriteria(String query, int page, int size);
    void deleteBook(long id);
//  Page<Book> getAllBook(int page, int size);

}
