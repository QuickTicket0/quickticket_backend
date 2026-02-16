package com.quickticket.quickticket.domain.event.mapper;

import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import com.quickticket.quickticket.domain.category.mapper.CategoryMapper;
import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.dto.EventRequest;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.domain.location.mapper.LocationMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        LocationMapper.class,
        CategoryMapper.class
    }
)
public interface EventMapper {
    @Mapping(target = "id", source = "eventId")
    Event toDomain(EventEntity entity);

    @Mapping(target = "eventId", ignore = true)
    @Mapping(target = "location", source = "locationEntity")
    @Mapping(target = "category1", source = "categoryEntity")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "ageRating", source = "dto.ageRating")
    @Mapping(target = "agentName", source = "dto.agentName")
    @Mapping(target = "views", constant = "0L")
    @Mapping(target = "userRatingSum", constant = "0L")
    EventEntity toEntity(EventRequest.Create dto,
                         LocationEntity locationEntity,
                         CategoryEntity categoryEntity);
}