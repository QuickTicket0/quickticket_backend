package com.quickticket.quickticket.domain.paymentMethod.dto;

import com.quickticket.quickticket.domain.paymentMethod.domain.PaymentMethodType;
import lombok.Builder;

import java.time.LocalDate;

public class PaymentMethodResponse {
    private interface MethodDetails {
    }

    @Builder
    private record CardPayment(
        String cardNumber,
        String cvs,
        LocalDate expirationPeroid,
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