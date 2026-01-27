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
    private final SeatPaymentIssueRepository seatPaymentRepository;
    private final SeatPaymentIssueMapper seatPaymentMapper;

    public List<SeatPaymentIssue> getSeatPaymentsByTicketIssueId(Long ticketId) {
        return seatPaymentRepository.getByTicketIssue_TicketIssueId(ticketId).stream()
                .map(seatPaymentMapper::toDomain)
                .toList();
    }

    public SeatPaymentIssue saveDomain(SeatPaymentIssue domain) {
        return seatPaymentRepository.saveDomain(domain);
    }
}
