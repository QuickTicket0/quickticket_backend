package com.quickticket.quickticket.domain.payment.seatPayment.repository;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.mapper.SeatPaymentIssueMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeatPaymentIssueRepositoryCustomImpl implements SeatPaymentIssueRepositoryCustom {
    private final SeatPaymentIssueMapper seatPaymentIssueMapper;

    @PersistenceContext
    private final EntityManager em;

    @Override
    public SeatPaymentIssue saveDomain(SeatPaymentIssue domain) {
        var entity = seatPaymentIssueMapper.toEntity(domain);

        if (entity.getPaymentId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }

        return seatPaymentIssueMapper.toDomain(entity);
    }
}
