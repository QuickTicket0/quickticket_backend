package com.quickticket.quickticket.domain.account.dto;

import com.quickticket.quickticket.shared.validators.NumberString;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class AccountRequest {
    public record Login(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 50)
        String password
    ) {}

    public record Signup(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 50)
        String password,

        @NotBlank
        @Size(max = 20)
        String realName,

        @NotNull
        @Past
        LocalDate birthday,

        @Email
        @Size(max = 30)
        String email,

        @NumberString
        @Size(max = 30)
        String phone
    ) {}

    public record Delete(
        @NotNull
        @Min(0)
        Long id,

        @NotBlank
        @Size(max = 50)
        String password
    ) {}
}
