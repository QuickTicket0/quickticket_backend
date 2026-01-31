package com.quickticket.quickticket.domain.payment.credit.service;

import com.quickticket.quickticket.domain.payment.credit.dto.CreditTransactionResponse;
import com.quickticket.quickticket.domain.payment.credit.repository.CreditTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CreditTransactionService {
    private final CreditTransactionRepository repository;

    public List<CreditTransactionResponse.Details> getResponseDetailsByUserId(Long userId) {
        return repository.getByUser_Id(userId).stream()
                .map(CreditTransactionResponse.Details::from)
                .toList();
    }
}
