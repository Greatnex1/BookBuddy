package com.ilearn.book_buddy.rest.response;

import com.ilearn.book_buddy.data.entity.Book;
import com.ilearn.book_buddy.rest.BookDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponse {
    private List<BookDto> books;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private int size;
    private int numberOfElementsInPage;
    Map<String, Object> pagedResponse;

}
