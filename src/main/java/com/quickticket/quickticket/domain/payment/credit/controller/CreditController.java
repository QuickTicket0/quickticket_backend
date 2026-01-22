package com.quickticket.quickticket.domain.payment.credit.controller;

import com.quickticket.quickticket.domain.payment.credit.domain.TransactionType;
import com.quickticket.quickticket.domain.payment.credit.dto.CreditTransactionResponse;
import com.quickticket.quickticket.domain.payment.credit.service.CreditTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreditController {
    private final CreditTransactionService service;

    @GetMapping("/myPage/myCredit")
    public String myCredit(Model model) {
        // 임시 리스트 데이터 생성
        List<CreditTransactionResponse.Details> historyList = new ArrayList<>();

        // 데이터 1: 충전 (양수)
        historyList.add(CreditTransactionResponse.Details.builder()
                .id(1L)
                .transactionType(TransactionType.CHARGE)
                .changeAmount(50000L)
                .balanceAfter(215000L)
                .createdAt(LocalDateTime.of(2026, 1, 20, 14, 30)) // 2026년 1월 20일
                .summary("크레딧 충전")
                .build());

        // 데이터 2: 사용 (음수)
        historyList.add(CreditTransactionResponse.Details.builder()
                .id(2L)
                .transactionType(TransactionType.SEAT_PAYMENT)
                .changeAmount(-165000L)
                .balanceAfter(165000L)
                .createdAt(LocalDateTime.of(2026, 1, 15, 10, 0)) // 2026년 1월 15일
                .summary("2026 이창섭 콘서트 예매")
                .build());

        // 데이터 3: 충전 (양수)
        historyList.add(CreditTransactionResponse.Details.builder()
                .id(3L)
                .transactionType(TransactionType.CHARGE)
                .changeAmount(100000L)
                .balanceAfter(330000L)
                .createdAt(LocalDateTime.of(2026, 1, 10, 9, 0)) // 2026년 1월 10일
                .summary("크레딧 충전")
                .build());

        // 최종 DTO
        CreditTransactionResponse response = CreditTransactionResponse.builder()
                .totalBalance(215000L) // 현재 총 잔액
                .transactionList(historyList)
                .build();

        // var details = service.getResponseDetailsByUserId();

        // 모델에 받아 넘김
        model.addAttribute("creditPage", response);
        return "myPage/myCredit";
    }
}
