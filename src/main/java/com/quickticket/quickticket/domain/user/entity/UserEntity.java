package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "USER")
@Getter
@Setter
public class UserEntity {
    // 값 타입인 long이 아닌 래퍼 타입 Long을 쓴 이유는
    // 엔티티 객체 최초 생성시 id값이 정해지지 않은 상태여서 null을 표현할 필요성이 있기 때문입니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long id;

    // @NotNull은 서버 앱 단계에서 null인지 검증을 해주고
    // @Column(nullable = false)는 DB 단계에서 NOT NULL 속성을 설정합니다
    @NotNull
    @Column(length = 30, nullable = false)
    private String username;

    @NotNull
    @Column(length = 100, nullable = false)
    private String password;

    @NotNull
    @Column(length = 30, nullable = false)
    private String realName;

    private LocalDate birthday;

    @Column(length = 40)
    private String email;

    @Column(length = 20)
    private String phone;

    @NotNull
    @Column(nullable = false)
    private Long credit = 0L;

    // 스프링부트 데이터 JPA에서 Enum 타입을 ORDINAL로 처리하면, Enum을 정의한 순서대로 번호를 매깁니다
    // 근데 이러면 사소한 순서 변경에도 DB로 읽고 쓰는 기준이 달라지니 유지보수가 힘듭니다
    // 근데 STRING으로 하면 문자열을 그대로 DB에 저장하기 때문에 성능 낭비가 큽니다
    // 따라서 Enum에 code 필드를 기준으로 convert하도록 합니다.
    @NotNull
    @Column(nullable = false)
    private AccountType type;

    @NotNull
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}