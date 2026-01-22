package com.quickticket.quickticket.domain.payment.seatPayment.service;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.mapper.SeatPaymentIssueMapper;
import com.quickticket.quickticket.domain.payment.seatPayment.repository.SeatPaymentIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatPaymentService {
    private final SeatPaymentIssueRepository repository;
    private final SeatPaymentIssueMapper mapper;

    public List<SeatPaymentIssue> getSeatPaymentsByTicketIssueId(Long ticketId) {
        return repository.getByTicketIssue_TicketIssueId(ticketId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
