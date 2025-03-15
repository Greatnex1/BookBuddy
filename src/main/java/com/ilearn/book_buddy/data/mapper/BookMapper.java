package com.ilearn.book_buddy.data.mapper;

import com.ilearn.book_buddy.data.entity.BookEntity;
import com.ilearn.book_buddy.data.model.Book;
import com.ilearn.book_buddy.rest.request.BookRequest;

public interface BookMapper {
    BookEntity toBookEntity(BookRequest book);
    BookRequest toBookObject(BookEntity bookEntity);
}
