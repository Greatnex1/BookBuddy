package com.ilearn.book_buddy.validator;

import com.ilearn.book_buddy.validator.annotation.ValidPayment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPaymentValidator implements ConstraintValidator<ValidPayment, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.matches("^(USSD|TRANSFER|ONLINE_BANKING)$");
    }
}
