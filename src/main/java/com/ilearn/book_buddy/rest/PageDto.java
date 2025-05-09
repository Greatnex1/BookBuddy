package com.ilearn.book_buddy.rest;

import com.ilearn.book_buddy.data.enums.ResponseStatus;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDto {
    private List<?> data;
    private boolean hasMore;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private ResponseStatus status;

    public static PageDto empty() {
        return PageDto.builder().data(new ArrayList<>()).totalPages(0).totalItems(0)
                .status(ResponseStatus.SUCCESS).build();
    }
    public static PageDto build(Page<?> page, Function<? super Object, ?> mapper) {
        List<?> data = page.stream().map(mapper).collect(Collectors.toList());
        return PageDto.builder().data(data).totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .hasMore(page.hasNext())
                .status(ResponseStatus.SUCCESS)
                .totalItems(page.getTotalElements()).build();
    }



}
