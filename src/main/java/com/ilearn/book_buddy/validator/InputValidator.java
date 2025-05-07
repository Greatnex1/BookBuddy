package com.ilearn.book_buddy.validator;

import com.ilearn.book_buddy.constants.ErrorMessages;

import org.apache.commons.lang3.StringUtils;


import static com.ilearn.book_buddy.constants.ErrorMessages.EMPTY_INPUT_ERROR;

public class InputValidator {

    public static void validateInput(String input, String data) {
        if(StringUtils.isEmpty(input)|| StringUtils.isBlank(input)|| StringUtils.isEmpty(input.trim())||
                input.equals(ErrorMessages.UNDEFINED)){
            throw new IllegalArgumentException(String.format(EMPTY_INPUT_ERROR, data));

        }
    }
    
    public static void validateInput(String input) {
        if(StringUtils.isEmpty(input)|| StringUtils.isBlank(input)|| StringUtils.isEmpty(input.trim())||
                input.equals(ErrorMessages.UNDEFINED)){

            throw new IllegalArgumentException(ErrorMessages.EMPTY_INPUT_ERROR);

        }
    }
}
