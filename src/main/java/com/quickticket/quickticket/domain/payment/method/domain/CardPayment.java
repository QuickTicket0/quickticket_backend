package com.quickticket.quickticket.domain.payment.method.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/// 결제 수단중 카드 결제 수단의 데이터
@Builder
@Getter
public class CardPayment extends PaymentMethod {
    private String cardNumber;
    private String cvs;
    private LocalDate expirationPeriod;
    private Boolean isActive;
    private String bankName;

    public PaymentMethodType getType() {
        return PaymentMethodType.CARD;
    }
}