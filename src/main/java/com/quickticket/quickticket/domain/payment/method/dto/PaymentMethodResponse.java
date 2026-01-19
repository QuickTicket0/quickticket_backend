package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import lombok.Builder;

import java.time.LocalDate;

public class PaymentMethodResponse {
    private interface MethodDetails {
    }

    @Builder
    private record CardPayment(
        String cardNumber,
        String cvs,
        LocalDate expirationPeriod,
        Boolean isActive,
        String bankName
    ) implements MethodDetails {}

    @Builder
    public record Details(
        Long id,
        PaymentMethodType methodType,
        MethodDetails methodDetails
    ) {}
}