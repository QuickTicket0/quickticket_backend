package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import lombok.Builder;

import java.time.LocalDate;

public class PaymentMethodResponse {
    /// 사용자가 본인의 결제수단 목록 전체를 조회할때
    @Builder
    public record Details(
        Long id,
        PaymentMethodCommonDto method
    ) {}
}