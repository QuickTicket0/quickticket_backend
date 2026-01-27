package com.quickticket.quickticket.domain.payment.seatPayment.repository;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.mapper.SeatPaymentIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public abstract class SeatPaymentIssueRepositoryImpl implements SeatPaymentIssueRepository {
    private final SeatPaymentIssueMapper seatPaymentIssueMapper;

    @Override
    public SeatPaymentIssue saveDomain(SeatPaymentIssue domain) {
        var entity = seatPaymentIssueMapper.toEntity(domain);
        this.save(entity);

        return seatPaymentIssueMapper.toDomain(entity);
    }
}
