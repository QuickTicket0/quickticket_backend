package com.quickticket.quickticket.domain.payment.seatPayment.repository;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.entity.SeatPaymentIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatPaymentIssueRepository extends JpaRepository<SeatPaymentIssueEntity, Long> {
    List<SeatPaymentIssueEntity> getByTicketIssue_TicketIssueId(Long ticketId);

    SeatPaymentIssue saveDomain(SeatPaymentIssue domain);
}
