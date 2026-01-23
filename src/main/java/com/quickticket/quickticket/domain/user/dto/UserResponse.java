package com.quickticket.quickticket.domain.user.dto;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
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

        AccountType role,
        
        LocalDateTime createdAt
    ) {
        public static Details from(UserEntity user) {
            return Details.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .realname(user.getRealName())
                    .birthday(user.getBirthday())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .credit(user.getCredit())
                    .role(user.getType())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }
}
