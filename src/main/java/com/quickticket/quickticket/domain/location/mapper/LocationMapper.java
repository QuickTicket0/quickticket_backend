package com.quickticket.quickticket.domain.location.mapper;

import com.quickticket.quickticket.domain.location.domain.Location;
import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true)
)
public interface LocationMapper {
    @Mapping(target = "id", source = "locationId")
    @Mapping(target = "name", source = "locationName")
    @Mapping(target = "siGunGu", source = "sigungu")
    @Mapping(target = "eupMyun", source = "eupmyun")
    @Mapping(target = "doroName", source = "doro")
    Location toDomain(LocationEntity entity);
}
