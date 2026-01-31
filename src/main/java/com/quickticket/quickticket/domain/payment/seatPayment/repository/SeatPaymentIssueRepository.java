package com.quickticket.quickticket.domain.payment.seatPayment.repository;

import com.quickticket.quickticket.domain.payment.seatPayment.entity.SeatPaymentIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatPaymentIssueRepository
        extends JpaRepository<SeatPaymentIssueEntity, Long>, SeatPaymentIssueRepositoryCustom {
    List<SeatPaymentIssueEntity> getByTicketIssue_TicketIssueId(Long ticketId);
}
