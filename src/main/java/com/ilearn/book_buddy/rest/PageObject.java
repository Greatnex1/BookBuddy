package com.ilearn.book_buddy.rest;

import com.ilearn.book_buddy.data.enums.ResponseStatus;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageObject {
    private ResponseStatus status;
    private List<?> data;
    private boolean hasMore;
    private int currentPage;
    private int totalPages;
    private long totalItems;

}
