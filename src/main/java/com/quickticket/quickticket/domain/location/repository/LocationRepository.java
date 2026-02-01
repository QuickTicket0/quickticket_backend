package com.quickticket.quickticket.domain.location.repository;

import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long>, LocationRepositoryCustom {
}
