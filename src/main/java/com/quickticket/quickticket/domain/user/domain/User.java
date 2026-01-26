package com.quickticket.quickticket.domain.user.domain;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/// 비밀번호나 인증 등은 account 도메인에서 관할하고, user 도메인은 사용자 데이터 처리에 집중합니다.
@Builder
@Getter
public class User implements UserDetails {
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

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}