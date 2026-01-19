package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentMethodCommonDto(
    @NotNull
    PaymentMethodType methodType,

    @NotNull
    PaymentMethodDetailsCommonDto methodDetails
) {}