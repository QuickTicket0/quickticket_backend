package com.quickticket.quickticket.domain.paymentMethod.entity;

import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PAYMENT_METHOD")
@Getter
@Setter
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long paymentMethodId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private User user;

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
