package com.quickticket.quickticket.domain.paymentMethod.dto;

import com.quickticket.quickticket.domain.paymentMethod.domain.PaymentMethodType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public class PaymentMethodRequest {
    private interface MethodDetails {
    }

    @Builder
    private record CardPayment(
        @NotBlank
        @Size(max = 20)
        String cardNumber,

        @NotBlank
        @Size(max = 4)
        String cvs,

        @NotNull
        LocalDate expirationPeriod,

        @NotNull
        Boolean isActive,

        @NotBlank
        @Size(max = 30)
        String bankName
    ) implements MethodDetails {}

    @Builder
    public record AddNew(
        @NotNull
        PaymentMethodType methodType,

        @NotNull
        MethodDetails methodDetails
    ) {}

    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}