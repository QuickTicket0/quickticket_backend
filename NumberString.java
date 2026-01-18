@Constraint(validatedBy = {PhoneNumberValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberString {
    String message() default "핸드폰 번호 양식에 맞지 않습니다. ex) 010-0000-0000";
    
    String regexp() {
        return "^\\d*$";
    }

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}