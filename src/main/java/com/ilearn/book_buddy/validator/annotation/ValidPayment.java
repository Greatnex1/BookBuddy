package com.ilearn.book_buddy.validator.annotation;

import com.ilearn.book_buddy.validator.ValidPaymentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidPaymentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPayment {
    String message() default "Invalid Payment Method";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
