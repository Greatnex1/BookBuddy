package com.ilearn.book_buddy.validator;

import com.ilearn.book_buddy.validator.annotation.ValidGenre;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidGenreValidator implements ConstraintValidator<ValidGenre, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^(FICTION|THRILLER|MYSTERY|POETRY|HORROR|SATIRE)$");

    }
}
