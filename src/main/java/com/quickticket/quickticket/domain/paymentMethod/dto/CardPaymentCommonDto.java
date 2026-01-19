package com.quickticket.quickticket.domain.paymentMethod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CardPaymentCommonDto(
    @NotBlank
    String cardNumber,

    @NotBlank
    String cvs,

    @NotBlank
    LocalDate expirationPeriod,

    @NotNull
    Boolean isActive,

    @NotBlank
    String bankName
) implements PaymentMethodDetailsCommonDto {}