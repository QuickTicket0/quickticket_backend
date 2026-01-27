package com.quickticket.quickticket.domain.payment.credit.service;

import com.quickticket.quickticket.domain.payment.credit.dto.CreditTransactionResponse;
import com.quickticket.quickticket.domain.payment.credit.repository.CreditTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CreditTransactionService {
    private final CreditTransactionRepository repository;

    public List<CreditTransactionResponse.Details> getResponseDetailsByUserId(Long userId) {
        return repository.getByUser_Id(userId).stream()
                .map(CreditTransactionResponse.Details::from)
                .toList();
    }
}
