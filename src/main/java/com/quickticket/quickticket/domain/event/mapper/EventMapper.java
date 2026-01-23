package com.quickticket.quickticket.domain.event.mapper;

import com.quickticket.quickticket.domain.category.mapper.CategoryMapper;
import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.mapper.LocationMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        LocationMapper.class,
        CategoryMapper.class
})
public interface EventMapper {
    @Mapping(target = "id", source = "eventId")
    Event toDomain(EventEntity entity);
}
