package com.ilearn.book_buddy.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    FICTION("FICTION"), THRILLER("THRILLER"), MYSTERY("MYSTERY"), HORROR("HORROR"), SATIRE("SATIRE"),POETRY("POETRY");
    private final String genre;
}
