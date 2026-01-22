package com.quickticket.quickticket.domain.payment.method.controller;

import com.quickticket.quickticket.domain.payment.method.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService service;

    @GetMapping("/myPage/paymentMethods")
    public String myPaymentMethods(Model model) {
        /*
        // 카드에 대한 정보
        CardPaymentCommonDto cardDetails = CardPaymentCommonDto.builder()
                .bankName("하나카드")
                .cardNumber("1234-5678-****-****")
                .cvs("123")
                .expirationPeriod(LocalDate.of(2030, 1, 1))
                .isActive(true)
                .build();

        // 결제수단에 대한 공통
        PaymentMethodCommonDto commonDto = PaymentMethodCommonDto.builder()
                .methodType(PaymentMethodType.CARD)
                .methodDetails(cardDetails)
                .build();

        // 리스트에 담기
        PaymentMethodResponse.Details details = PaymentMethodResponse.Details.builder()
                .id(1L)
                .method(commonDto)
                .build();

        // var details = service.getResponseDetailsByUserId();

        // 모델에 담아 넘김
        List<PaymentMethodResponse.Details> methodList = List.of(details);
        model.addAttribute("methodList", methodList);
        */

        return "myPage/payments";
    }
}
