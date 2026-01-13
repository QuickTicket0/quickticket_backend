package com.quickticket.quickticket.domain.paymentMethod.entity;

import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
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
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long paymentMethodId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private PaymentMethodType type;

    @Column(columnDefinition = "VARCHAR(20)", length = 20)
    private String cardNumber;

    @Column(columnDefinition = "SMALLINT UNSIGNED")
    private short cvs;

    private LocalDate expirationPeriod;

    private boolean isActive;

    @Column(columnDefinition = "VARCHAR(20)")
    private String bank;

    @Column(columnDefinition = "VARCHAR(20)")
    private String accountNumber;
}
