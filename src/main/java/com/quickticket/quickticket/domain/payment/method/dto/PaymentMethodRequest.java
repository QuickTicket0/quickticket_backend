package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

public class PaymentMethodRequest {
    /// 사용자가 마이페이지에서 새 결제수단을 추가할때
    @Builder
    public record AddNew(
        @NotNull
        PaymentMethodCommonDto method
    ) {}

    /// 사용자가 마이페이지에서 기존 결제수단을 제거할때
    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}