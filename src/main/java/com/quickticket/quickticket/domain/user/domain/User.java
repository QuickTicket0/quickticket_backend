package com.quickticket.quickticket.domain.user.domain;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/// 비밀번호나 인증 등은 account 도메인에서 관할하고, user 도메인은 사용자 데이터 처리에 집중합니다.
@Builder
@Getter
public class User {
    private Long id;
    private String username;
    private String realName;
    private String password;
    private LocalDate birthday;
    private String email;
    private String phone;
    /// 현재 보유한 크레딧 잔액
    @Builder.Default
    private Long credit = 0L;
    private AccountType type;
    private LocalDateTime createdAt;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}