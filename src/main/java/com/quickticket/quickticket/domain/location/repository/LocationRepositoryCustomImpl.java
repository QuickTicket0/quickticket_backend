package com.quickticket.quickticket.domain.location.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocationRepositoryCustomImpl implements LocationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * location ID로 조회하여 LocationCommonDto 변환하여 반환
     * @param locationId 조회할 location PK
     * @return 조회된 location 정보를 담은 LocationCommonDto (없을 경우엔 null로 반환)
     */
    public LocationCommonDto getLocationById(Long locationId) {
            var location = QLocationEntity.locationEntity;

            var query =  Optional.ofNullable(queryFactory
                        .select(location)
                        .from(location)
                        .where(location.locationId.eq(locationId))
                        .fetchOne()
                )
                .map(LocationCommonDto::from)
                .orElse(null);
        return query;
    }
}
