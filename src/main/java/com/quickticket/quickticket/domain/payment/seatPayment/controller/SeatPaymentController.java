package com.quickticket.quickticket.domain.payment.seatPayment.controller;

import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.domain.payment.method.dto.CardPaymentCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SeatPaymentController {
    @GetMapping("/myPage/payments")
    public String myPayments(Model model) {
        return "myPage/payments";
    }
}