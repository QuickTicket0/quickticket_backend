package com.quickticket.quickticket.domain.location.mapper;

import com.quickticket.quickticket.domain.location.domain.Location;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
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

    /**
     * LocationCommonDto 정보를 바탕으로 콘서트 등록에서 사용하는 장소(Location) 엔티티로 변환
     * @param dto LocationCommonDto
     * @return LocationEntity
     */
    @Mapping(target = "locationId", ignore = true)
    @Mapping(target = "locationName", source = "name")
    @Mapping(target = "doro", source = "doroName")
    @Mapping(target = "sigungu", source = "siGunGu")
    @Mapping(target = "eupmyun", source = "eupMyun")
    LocationEntity toEntity(LocationCommonDto dto);
}
