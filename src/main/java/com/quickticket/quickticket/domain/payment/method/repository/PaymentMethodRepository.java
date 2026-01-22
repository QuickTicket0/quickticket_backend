package com.quickticket.quickticket.domain.payment.method.repository;

import com.quickticket.quickticket.domain.payment.method.entity.PaymentMethodEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
    List<PaymentMethodEntity> getByUser_Id(Long userId);
}
