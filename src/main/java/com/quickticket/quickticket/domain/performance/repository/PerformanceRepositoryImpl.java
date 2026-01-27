package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.mapper.PerformanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public abstract class PerformanceRepositoryImpl implements PerformanceRepository {
    private final PerformanceMapper performanceMapper;

    @Override
    public Performance saveDomain(Performance domain) {
        var entity = performanceMapper.toEntity(domain);

        entity = this.save(entity);
        return performanceMapper.toDomain(entity);
    }
}
