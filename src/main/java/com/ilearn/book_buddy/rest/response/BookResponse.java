package com.ilearn.book_buddy.rest.response;

import com.ilearn.book_buddy.data.enums.BookType;
import com.ilearn.book_buddy.data.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BookResponse {
    private String title;
    private String description;
    private String isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private String reference;
    private String url;
}
