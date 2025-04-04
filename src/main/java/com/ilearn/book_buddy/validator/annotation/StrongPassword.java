package com.ilearn.book_buddy.validator.annotation;

import com.ilearn.book_buddy.validator.StrongPasswordValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StrongPassword {

}
