package com.quickticket.quickticket.domain.user.entity;

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
public class User {
    // MySQL에서의 INT UNSIGNED 타입은 부호가 없기에 담을 수 있는 숫자의 범위가 0 ~ 4,294,967,295 입니다
    // 반면 자바의 int 타입의 범위는 -2,147,483,648 ~ 2,147,483,647 이기 때문에 오버플로우의 여지가 있습니다
    // 따라서 안전하게 더 큰 long 타입을 써서 이를 예방합니다.
    //
    // 값 타입인 long이 아닌 래퍼 타입 Long을 쓴 이유는
    // 엔티티 객체 최초 생성시 id값이 정해지지 않은 상태여서 null을 표현할 필요성이 있기 때문입니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long id;

    @Column(columnDefinition = "VARCHAR(30)", length = 30, nullable = false)
    private String username;

    // SHA-256 해싱을 사용하면 256비트 즉 32바이트의 데이터가 나옵니다
    // 일반적인 CHAR 타입보다 BINARY 타입으로 설정하는게 메모리 절약성 측면에서 우수합니다
    // 그 이유를 알려면 CHAR 타입이 문자를 어떻게 저장하는지 알아보면 됩니다.
    @Column(columnDefinition = "BINARY(32)", length = 32, nullable = false)
    private byte[] password;

    @Column(columnDefinition = "VARCHAR(30)", length = 30, nullable = false)
    private String name;

    private LocalDate birthday;

    @Column(columnDefinition = "VARCHAR(40)", length = 40)
    private String email;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String phone;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long credit;

    // 스프링부트 데이터 JPA에서 Enum 타입을 ORDINAL로 처리하면, Enum을 정의한 순서대로 번호를 매깁니다
    // 근데 이러면 사소한 순서 변경에도 DB로 읽고 쓰는 기준이 달라지니 유지보수가 힘듭니다
    // 근데 STRING으로 하면 문자열을 그대로 DB에 저장하기 때문에 성능 낭비가 큽니다
    // 따라서 Enum에 code 필드를 기준으로 convert하도록 합니다.
    //
    // UserRoleConverter에 @Converter(autoApply = true)를 설정했기 때문에 별도의 어노테이션을 달 필요 없습니다.
    @NotNull
    private UserRole role;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}