package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/// methodDetails에서 type을 포함해서 상속을 받는 Common Dto
@Builder
public record PaymentMethodCommonDto(
    @NotNull
    PaymentMethodType methodType,

    /// 결제수단 Dto도 상속을 구현했기 때문에 각 수단에 맞는 형식이 맞춰서 들어갑니다.
    /// 그 형식은 위의 methodType에 대응되는 것이어야 합니다.
    @NotNull
    PaymentMethodDetailsCommonDto methodDetails
) {
    public static PaymentMethodCommonDto from(PaymentMethodEntity method) {
        switch (method.getType()) {
            case CARD -> {
                return PaymentMethodCommonDto.builder()
                    .methodType(PaymentMethodType.CARD)
                    .methodDetails(CardPaymentCommonDto.from(method))
                    .build();
            }
            case CREDIT -> {
                throw new RuntimeException("미구현 기능");
            }
            default -> {
                throw new IllegalStateException();
            }
        }
    }
}