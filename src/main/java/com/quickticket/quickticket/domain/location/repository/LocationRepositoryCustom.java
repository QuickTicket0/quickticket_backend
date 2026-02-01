package com.quickticket.quickticket.domain.location.repository;

import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;

public interface LocationRepositoryCustom {
    LocationCommonDto getLocationById(Long locationId);
}
