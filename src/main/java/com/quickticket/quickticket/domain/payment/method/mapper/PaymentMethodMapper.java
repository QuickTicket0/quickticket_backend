package com.quickticket.quickticket.domain.payment.method.mapper;

import com.quickticket.quickticket.domain.payment.method.domain.CardPayment;
import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true)
)
public abstract class PaymentMethodMapper {
    @Autowired private UserMapper userMapper;

    public PaymentMethod toDomain(PaymentMethodEntity entity) {
        switch (entity.getType()) {
            case null -> {
                throw new IllegalStateException("PaymentMethodEntity의 type은 null이 될 수 없습니다.");
            }
            case CARD -> {
                return CardPayment.builder()
                        .id(entity.getPaymentMethodId())
                        .user(userMapper.toDomain(entity.getUser()))
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
