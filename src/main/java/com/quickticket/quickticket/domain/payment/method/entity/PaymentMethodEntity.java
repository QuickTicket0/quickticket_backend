package com.quickticket.quickticket.domain.payment.method.entity;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT_METHOD")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentMethodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long paymentMethodId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private UserEntity user;

    @NotNull
    @Column(nullable = false)
    private PaymentMethodType type;

    @Column(length = 20)
    private String cardNumber;

    @Column(length = 4)
    private String cvs;

    private LocalDate expirationPeriod;

    private Boolean isActive;

    @Column(length = 20)
    private String bank;

    @Column(length = 20)
    private String accountNumber;
}
