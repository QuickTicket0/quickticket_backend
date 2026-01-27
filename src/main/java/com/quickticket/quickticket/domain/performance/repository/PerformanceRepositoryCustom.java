package com.quickticket.quickticket.domain.performance.repository;

import com.quickticket.quickticket.domain.performance.domain.Performance;

public interface PerformanceRepositoryCustom {
    Performance saveDomain(Performance domain);
}
