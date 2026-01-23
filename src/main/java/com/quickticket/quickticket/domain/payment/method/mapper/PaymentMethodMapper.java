package com.quickticket.quickticket.domain.payment.method.mapper;

import com.quickticket.quickticket.domain.payment.method.domain.CardPayment;
import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class
})
public interface PaymentMethodMapper {
    default PaymentMethod toDomain(PaymentMethodEntity entity) {
        switch (entity.getType()) {
            case null -> {
                throw new IllegalStateException("PaymentMethodEntity의 type은 null이 될 수 없습니다.");
            }
            case CARD -> {
                return CardPayment.builder()
                        .cardNumber(entity.getCardNumber())
                        .cvs(entity.getCvs())
                        .expirationPeriod(entity.getExpirationPeriod())
                        .isActive(entity.getIsActive())
                        .bankName(entity.getBank())
                        .build();
            }
            case CREDIT -> {
                throw new RuntimeException("아직 구현하지 않음");
            }
            default -> {
                throw new RuntimeException("아직 구현하지 않음");
            }
        }
    };
}
