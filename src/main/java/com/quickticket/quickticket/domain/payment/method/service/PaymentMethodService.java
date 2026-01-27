package com.quickticket.quickticket.domain.payment.method.service;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethod;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodResponse;
import com.quickticket.quickticket.domain.payment.method.mapper.PaymentMethodMapper;
import com.quickticket.quickticket.domain.payment.method.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public List<PaymentMethodResponse.Details> getResponseDetailsByUserId(Long userId) {
        return paymentMethodRepository.getByUser_Id(userId).stream()
                .map(PaymentMethodResponse.Details::from)
                .toList();
    }

    public PaymentMethod getDomainById(Long methodId) {
        var methodEntity = paymentMethodRepository.getReferenceById(methodId);

        return paymentMethodMapper.toDomain(methodEntity);
    }
}
