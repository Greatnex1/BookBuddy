package com.ilearn.book_buddy.handlers.exception;

import com.ilearn.book_buddy.rest.ErrorResponseWithArgsDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomBindRuntimeException extends RuntimeException {
    private ErrorResponseWithArgsDto.ErrorWithArguments[] errorWithArguments;

    public CustomBindRuntimeException(ErrorResponseWithArgsDto.ErrorWithArguments... errorWithArguments) {
        this.errorWithArguments = errorWithArguments;
    }

    public CustomBindRuntimeException(String s, Object... args) {
        this.errorWithArguments = new ErrorResponseWithArgsDto.ErrorWithArguments[1];
        this.errorWithArguments[0] = new ErrorResponseWithArgsDto.ErrorWithArguments(s, args);
    }}
