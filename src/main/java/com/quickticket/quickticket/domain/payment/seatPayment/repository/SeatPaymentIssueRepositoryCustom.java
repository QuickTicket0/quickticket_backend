package com.quickticket.quickticket.domain.payment.seatPayment.repository;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import org.springframework.data.repository.NoRepositoryBean;

public interface SeatPaymentIssueRepositoryCustom {
    SeatPaymentIssue saveDomain(SeatPaymentIssue domain);
}
