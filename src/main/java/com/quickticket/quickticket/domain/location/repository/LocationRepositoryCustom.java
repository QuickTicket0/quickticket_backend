package com.quickticket.quickticket.domain.location.repository;

import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import org.springframework.cache.annotation.Cacheable;

public interface LocationRepositoryCustom {
    @Cacheable(value = "cache:location-common-dto", key = "#locationId")
    LocationCommonDto getCommonDtoById(Long locationId);
}
