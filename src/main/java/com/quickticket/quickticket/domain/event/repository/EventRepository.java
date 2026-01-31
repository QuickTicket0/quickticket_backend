package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> getById(Long id);

    List<EventEntity> getAllByOrderByViewsDesc(Pageable pageable);
}

