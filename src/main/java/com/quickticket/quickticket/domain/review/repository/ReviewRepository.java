package com.quickticket.quickticket.domain.review.repository;

import com.quickticket.quickticket.domain.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> getByEvent_EventId(Long eventId);
}
