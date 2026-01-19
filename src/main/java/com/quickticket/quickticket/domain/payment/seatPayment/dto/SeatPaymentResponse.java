package com.quickticket.quickticket.domain.payment.seatPayment.dto;

import java.util.List;

public class SeatPaymentResponse {
    public record IssueWithPaymentMethod(
        String method,
        String status,
        String createdAt,
        Integer amount,
        List<SeatClass> paidSeats
    ) {}

    public record SeatClass (
        String name,
        Integer quantity
    ) {}
}
