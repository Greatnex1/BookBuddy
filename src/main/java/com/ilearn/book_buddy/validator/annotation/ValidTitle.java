package com.ilearn.book_buddy.validator.annotation;

import com.ilearn.book_buddy.validator.ValidTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidTitleValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidTitle{
    String message() default "Title must contain only numbers and letters";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
