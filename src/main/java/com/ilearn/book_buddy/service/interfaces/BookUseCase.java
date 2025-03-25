package com.ilearn.book_buddy.service.interfaces;

import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.rest.BookDto;
import com.ilearn.book_buddy.rest.PageObject;
import com.ilearn.book_buddy.rest.request.BookRequest;
import com.ilearn.book_buddy.rest.response.PagedResponse;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.io.IOException;
import java.util.List;

public interface BookUseCase {
    BookDto createBook(BookRequest bookRequest) throws IOException;
    void updateBook(long id, BookRequest bookRequest) throws IOException;
    PagedResponse findAll(int pageNumber, int noOfItems);
    SpringDataJaxb.PageDto searchCriteria(String query, int page, int size);
    void deleteBook(long id);
    List<BookDto> filterByAuthor(String reference);

}
