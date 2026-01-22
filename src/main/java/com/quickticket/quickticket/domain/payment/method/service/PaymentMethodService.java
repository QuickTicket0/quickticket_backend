package com.quickticket.quickticket.domain.payment.method.service;

import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodResponse;
import com.quickticket.quickticket.domain.payment.method.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository repository;

    public List<PaymentMethodResponse.Details> getResponseDetailsByUserId(Long userId) {
        return repository.getByUser_Id(userId).stream()
                .map(PaymentMethodResponse.Details::from)
                .collect(Collectors.toList());
    }
}
