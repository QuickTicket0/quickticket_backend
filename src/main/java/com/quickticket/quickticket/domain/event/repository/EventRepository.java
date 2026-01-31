package com.quickticket.quickticket.domain.event.repository;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> getById(Long id);

    @Cacheable(value = "EventRepository_getAgeRatingById", key = "#id")
    Optional<AgeRating> getAgeRatingById(Long id);

    List<EventEntity> getAllByOrderByViewsDesc(Pageable pageable);
}
