package com.quickticket.quickticket.domain.location.repository;

import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import org.springframework.cache.annotation.Cacheable;

public interface LocationRepositoryCustom {
    @Cacheable(value = "LocationRepositoryCustom_getCommonDtoById", key = "#locationId")
    LocationCommonDto getCommonDtoById(Long locationId);
}
