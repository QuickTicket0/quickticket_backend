package com.quickticket.quickticket.domain.payment.credit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CreditController {
    @GetMapping("/myPage/myCredit")
    public String myCredit(Model model) { return "myPage/myCredit"; }
}
