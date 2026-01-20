package com.quickticket.quickticket.domain.payment.method.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CardPaymentCommonDto(
    @NotBlank
    @Size(max = 20)
    String cardNumber,

    @NotBlank
    @Size(max = 4)
    String cvs,

    @NotBlank
    LocalDate expirationPeriod,

    @NotNull
    Boolean isActive,

    @NotBlank
    @Size(max = 30)
    String bankName
) implements PaymentMethodDetailsCommonDto {}