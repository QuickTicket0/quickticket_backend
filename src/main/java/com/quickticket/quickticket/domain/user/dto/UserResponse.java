package com.quickticket.quickticket.domain.user.dto;

import com.quickticket.quickticket.domain.user.domain.UserRole;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {
    @Builder
    public record Details(
        Long id,

        String username,

        String realname,

        LocalDate birthday,

        String email,

        String phone,

        Long credit,

        UserRole role,
        
        LocalDateTime createdAt
    ) {}
}
