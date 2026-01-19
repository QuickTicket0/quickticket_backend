package com.quickticket.quickticket.shared.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {NumberString.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberString {
    String message() default "핸드폰 번호 양식에 맞지 않습니다. ex) 010-0000-0000";
    
    String regexp() default "^\\d*$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}