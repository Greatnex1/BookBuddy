package com.ilearn.book_buddy.data.mapper;

import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.rest.request.BookRequest;

public interface BookMapper {
    Book toBookEntity(BookRequest book);
    BookRequest toBookObject(Book book);
}
