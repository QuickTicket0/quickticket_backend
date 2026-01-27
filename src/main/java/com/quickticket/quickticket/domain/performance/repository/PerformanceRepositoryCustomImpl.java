package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {
    private final PerformanceMapper performanceMapper;

    @PersistenceContext
    private final EntityManager em;

    public Performance saveDomain(Performance domain) {
        var entity = performanceMapper.toEntity(domain);

        if (entity.getPerformanceId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }

        return performanceMapper.toDomain(entity);
    }
}
