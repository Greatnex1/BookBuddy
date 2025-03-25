package com.ilearn.book_buddy.service.implementation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilearn.book_buddy.data.entity.Author;
import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.handlers.exception.GenericException;
import com.ilearn.book_buddy.handlers.messages.ErrorMessages;
import com.ilearn.book_buddy.repository.BookRepository;
import com.ilearn.book_buddy.rest.BookDto;
import com.ilearn.book_buddy.rest.PageDto;
import com.ilearn.book_buddy.rest.PageObject;
import com.ilearn.book_buddy.rest.request.BookRequest;
import com.ilearn.book_buddy.rest.response.PagedResponse;
import com.ilearn.book_buddy.service.interfaces.BookUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.ilearn.book_buddy.constants.ErrorMessages.ALREADY_EXIST;
import static com.ilearn.book_buddy.constants.ErrorMessages.NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
//@ToString
public class BookService implements BookUseCase {

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookRequest bookRequest) {
        log.info("Creating new book {}", bookRequest);
        Optional<Book> foundBook = bookRepository.findBookByIsbn(bookRequest.getIsbn());
        if (foundBook.isPresent()) {
            throw new GenericException(ALREADY_EXIST, HttpStatus.BAD_REQUEST);
        }
        Book book = new Book();
        BeanUtils.copyProperties(bookRequest, book);
        book.setPublishDate(LocalDate.parse(bookRequest.getPublishedDate()));
        bookRepository.save(book);
        return BookDto.toDto(book);
    }


    @Override
    public BookDto findById(String id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
        return BookDto.toDto(book);
    }

    @Override
    public void updateBook(String id, BookRequest bookRequest) {
        Book book = this.findBook(id);
        BeanUtils.copyProperties(bookRequest, book);
        book.setPublishDate(LocalDate.parse(bookRequest.getPublishedDate()));
        bookRepository.save(book);
    }

    @Override
    public PagedResponse findAll(int pageNumber, int noOfItems) {
        Pageable pageable = PageRequest.of(pageNumber, noOfItems, Sort.by("name"));
        Page<Book> page = bookRepository.findAll(pageable);

        PagedResponse pagedResponse = new PagedResponse();
        pagedResponse.setBooks(mapBookToBookDto(page.getContent()));
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setTotalPages(page.getTotalPages());
        pagedResponse.setTotalElements(page.getTotalElements());
        pagedResponse.setSize(page.getSize());
        pagedResponse.setNumberOfElementsInPage(page.getNumberOfElements());
        return pagedResponse;
    }

    private static List<BookDto> mapBookToBookDto(List<Book> books) {
        return books.stream().map(BookDto::fromModel).collect(Collectors.toList());
    }


    /* This can also be implemented as soft delete where the record is not deleted but made invisible by adding a boolean field to the Book column i.e deleted - true or false */
    @Override
    public void deleteBook(String id) {
        Book book = this.findBook(id);
        bookRepository.delete(book);
    }


    private Book findBook(String id) {
        return bookRepository.findById(id).orElseThrow(() -> new GenericException(NOT_FOUND, HttpStatus.NOT_FOUND));
    }

}