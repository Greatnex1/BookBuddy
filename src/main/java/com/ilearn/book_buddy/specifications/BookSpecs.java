package com.ilearn.book_buddy.specifications;

import com.ilearn.book_buddy.data.entity.Book;

public class BookSpecs extends QueryToCriteria<Book>{
    public BookSpecs(String query) {
        super(query);
    }
}
