package com.quickticket.quickticket.domain.paymentMethod.dto;

import com.quickticket.quickticket.domain.paymentMethod.domain.PaymentMethodType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentMethodCommonDto(
    @NotNull
    PaymentMethodType methodType,

    @NotNull
    PaymentMethodDetailsCommonDto methodDetails
) {}