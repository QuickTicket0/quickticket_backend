package com.quickticket.quickticket.domain.payment.seatPayment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SeatPaymentController {
    @GetMapping("/myPage/payments")
    public String myPayments(Model model) {

        // 결제정보를 리스트 형태로 넘겼다고 가정함
        //model.addAttribute("paymentList", paymentList);

        return "myPage/payments";
    }
}