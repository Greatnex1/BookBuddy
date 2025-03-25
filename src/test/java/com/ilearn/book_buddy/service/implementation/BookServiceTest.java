package com.ilearn.book_buddy.service.implementation;

import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.data.enums.BookType;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.repository.BookRepository;
import com.ilearn.book_buddy.rest.BookDto;
import com.ilearn.book_buddy.rest.request.BookRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
class BookServiceTest {

    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    void createBook_success() throws IOException {
        BookRequest bookRequest = new BookRequest();
        when(bookRepository.findBookByIsbn(bookRequest.getIsbn())).thenReturn(Optional.empty());

        Book savedBook = new Book();
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookDto createdBook = bookService.createBook(bookRequest);

        assertNotNull(createdBook);
        assertEquals("Book One", createdBook.getTitle());
        assertEquals(12345, createdBook.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_duplicateIsbn_throwsException() {
        BookRequest bookRequest = new BookRequest();
        Book existingBook = new Book();

        when(bookRepository.findBookByIsbn(bookRequest.getIsbn())).thenReturn(Optional.of(existingBook));

        GenericException exception = assertThrows(GenericException.class, () -> bookService.createBook(bookRequest));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void findById_success() {
        String bookId = "3334567-3-r";
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        BookDto bookDto = bookService.findById(bookId);

        assertNotNull(bookDto);
        assertEquals("Book One", bookDto.getTitle());
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void findById_notFound_throwsException() {
        String bookId = "3334567";
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> bookService.findById(bookId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        verify(bookRepository, times(1)).findById(bookId);
    }
}