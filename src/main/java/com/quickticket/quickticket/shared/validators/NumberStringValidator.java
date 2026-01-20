package com.quickticket.quickticket.shared.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/// 오직 숫자로만 이뤄진 문자열 validator
public class NumberStringValidator implements ConstraintValidator<NumberString, String> {
    private String regexp;

    @Override
    public void initialize(NumberString constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Pattern.matches(this.regexp, value);
    }
}
