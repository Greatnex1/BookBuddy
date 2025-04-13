package com.ilearn.book_buddy.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ilearn.book_buddy.data.enums.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private ResponseStatus status;
    private Object data;
    private String message;

    @JsonIgnore
    private Object[] messageArgs;

    public static ResponseDto wrapSuccessResult(Object data, String message) {
        ResponseDto response = new ResponseDto();
        response.setData(data);
        response.setMessage(message);
        response.setStatus(ResponseStatus.SUCCESS);
        return response;
    }

    public static ResponseDto wrapErrorResult(String message) {
        ResponseDto response = new ResponseDto();
        response.setStatus(ResponseStatus.ERROR);
        response.setMessage(message);
        return response;
    }

    /**
     * This method is often used in web services or APIs to send responses to clients in a consistent format.
     * By using this method, the response is wrapped in a standard format, which can be easily parsed by the
     * client applications.
     */
}
