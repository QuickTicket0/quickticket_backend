package com.quickticket.quickticket.domain.paymentMethod.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaymentMethodController {
    @GetMapping("/myPage/payments")
    public String myPayments(Model model) {
        return "myPage/payments";
    }

    @GetMapping("/myPage/myCredit")
    public String myCredit(Model model) { return "myPage/myCredit"; }

}