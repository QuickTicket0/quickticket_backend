package com.quickticket.quickticket.domain.ticket.mapper;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventResponseMapper {
    default TicketResponse.EventInfo toResponse(EventEntity entity) {
        return TicketResponse.EventInfo.builder()
                .ageRating(entity.getAgeRating())
//                .casting(entity)
                .location(LocationCommonDto.from(entity.getLocation()))
                .name(entity.getName())
//                .range()
                .thumbnailImage(entity.getThumbnailImage())
                .build();
    };
}
