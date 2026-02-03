package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> getEntityById(Long id);

    @Cacheable(value = "EventRepository_getAgeRatingById", key = "#id")
    Optional<AgeRating> getAgeRatingById(Long id);

    List<EventEntity> getAllByOrderByViewsDesc(Pageable pageable);
}

