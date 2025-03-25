package com.ilearn.book_buddy.rest;

import com.ilearn.book_buddy.data.entity.Book;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String title;
    private String description;
    private String isbn;
    private int publicationYear;
    private long price;
    private LocalDate dateUploaded;
    private String reference;
    private String url;
    private List<AuthorDto> authors;

    public static BookDto fromModel(Book book){
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        bookDto.setAuthors(book.getAuthors().stream().map(author -> {
            var authorDto = new AuthorDto();
            BeanUtils.copyProperties(author, authorDto);
            return authorDto;
        }).toList());
        return bookDto;
    }

    public static BookDto toDto(Object o){
        return fromModel((Book) o);
    }

}

