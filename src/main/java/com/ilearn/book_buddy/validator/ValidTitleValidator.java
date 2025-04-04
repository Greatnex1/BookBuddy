package com.ilearn.book_buddy.validator;

import com.ilearn.book_buddy.validator.annotation.ValidTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTitleValidator implements ConstraintValidator<ValidTitle, String> {


    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^[a-zA-Z0-9 ]+$");
    }
}
