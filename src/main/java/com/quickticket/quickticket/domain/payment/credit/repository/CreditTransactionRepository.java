package com.quickticket.quickticket.domain.payment.credit.repository;

import com.quickticket.quickticket.domain.payment.credit.entity.CreditTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditTransactionRepository extends JpaRepository<CreditTransactionEntity, Long> {
    List<CreditTransactionEntity> getByUser_Id(Long userId);
}
