package com.ilearn.book_buddy.rest.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private Instant timestamp;
    private String message;
    private String path;
}
