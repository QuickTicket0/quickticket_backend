package com.quickticket.quickticket.domain.payment.method.dto;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.util.Assert;

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
) implements PaymentMethodDetailsCommonDto {
    public static CardPaymentCommonDto from(PaymentMethodEntity paymentMethod) {
        Assert.state(
                paymentMethod.getType() == PaymentMethodType.CARD,
                "CardPaymentCommonDto는 type이 CARD인 엔티티에서만 생성할 수 있습니다."
        );

        return CardPaymentCommonDto.builder()
                .bankName(paymentMethod.getBank())
                .cardNumber(paymentMethod.getCardNumber())
                .cvs(paymentMethod.getCvs())
                .expirationPeriod(paymentMethod.getExpirationPeriod())
                .isActive(paymentMethod.getIsActive())
                .build();
    }

    /**
     * PaymentMethod를 CardPaymentCommonDto로 변환
     * @param paymentMethod
     * @return 카드 정보가 담긴 상세 DTO
     * @throws IllegalStateException 결제 타입이 CARD가 아닐 경우 발생
     */
    public static CardPaymentCommonDto from(PaymentMethod paymentMethod) {
        Assert.state(
                paymentMethod.getType() == com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType.CARD,
                "CardPaymentCommonDto는 type이 CARD인 객체에서만 생성할 수 있습니다."
        );

        var card = (com.quickticket.quickticket.domain.payment.method.domain.CardPayment) paymentMethod;

        return CardPaymentCommonDto.builder()
                .bankName(card.getBankName())
                .cardNumber(card.getCardNumber())
                .cvs(card.getCvs())
                .expirationPeriod(card.getExpirationPeriod())
                .isActive(card.getIsActive())
                .build();
    }
}