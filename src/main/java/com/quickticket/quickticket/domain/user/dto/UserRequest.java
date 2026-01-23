package com.quickticket.quickticket.domain.user.dto;

import com.quickticket.quickticket.shared.validators.NumberString;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

public class UserRequest {
    @Builder
    public record Login(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 50)
        String password
    ) {}

    @Builder
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

    /// 사용자 설정 변경
    /// 바꿀 정보만 삽입하고, 나머지는 null로 두면 기존 설정이 적용됩니다
    @Builder
    public record EditInfo(
        @NotNull
        @Min(0)
        Long id,

        @Size(max = 50)
        String username,

        @Size(max = 50)
        String password,

        @Size(max = 20)
        String realName,

        @Past
        LocalDate birthday,
        
        @Email
        @Size(max = 30)
        String email,

        @NumberString
        @Size(max = 30)
        String phone
    ) {}
    
    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id,

        @NotBlank
        @Size(max = 50)
        String password
    ) {}
}