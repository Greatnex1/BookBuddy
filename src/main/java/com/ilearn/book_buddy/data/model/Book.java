package com.ilearn.book_buddy.data.model;

import com.ilearn.book_buddy.data.entity.BookEntity;
import com.ilearn.book_buddy.data.enums.BookType;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
   private  String id;

    private String name;

    private int isbn;

    private LocalDate publishDate;

    private BigDecimal price;

    private BookType bookType;

    private Date createdAt;

    private Date updatedAt;

    private String createdBy;


    public static Book fromModel(BookEntity book){
        Book bookDto = new Book();
        BeanUtils.copyProperties(book, bookDto);
//        bookDto.setId(book.getReference());
        return bookDto;
    }

    public static Book toEntity(Object o){
        return fromModel((BookEntity) o);
    }
}
