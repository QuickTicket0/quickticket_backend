package com.quickticket.quickticket.shared.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {NumberStringValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberString {
    String message() default "오직 숫자로 이뤄진 문자열이어야 합니다. ex) 0123210213";
    
    String regexp() default "^\\d*$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}