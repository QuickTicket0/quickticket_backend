package com.quickticket.quickticket.domain.seatPaymentIssue.dto;

import java.util.List;

public class SeatPaymentIssueResponse {
    public record WithPaymentMethod(
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
