package com.quickticket.quickticket.domain.seat.mapper;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.dto.EventRequest;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.seat.domain.SeatArea;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatAreaId;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatClassId;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        EventMapper.class
    }
)
public interface SeatClassMapper {
    @Mapping(target = "id", source = "id.seatClassId")
    SeatClass toDomain(SeatClassEntity entity);

    @Mapping(target = "id", expression = "java(seatClassIdToEntity(domain))")
    SeatClassEntity toEntity(SeatClass domain);

    /**
     * 좌석 등급 DTO, EventEntity, SeatClassId를 합쳐서 SeatClassEntity를 생성
     * @param dto         화면에서 넘어온 좌석 정보
     * @param eventEntity 현재 좌석이 속한 콘서트(Event) 엔티티
     * @param id          seatClassId, eventId 정보를 담고 있는 객체
     * @return            SeatClassEntity 객체
     */
    @Mapping(target = "id", source = "id")
    @Mapping(target = "event", source = "eventEntity")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "price", source = "dto.price")
    SeatClassEntity toEntity(EventRequest.SeatGrade dto, EventEntity eventEntity, SeatClassId id);

    default SeatClassId seatClassIdToEntity(SeatClass domain) {
        return new SeatClassId(domain.getId(), domain.getEvent().getId());
    }

    /**
     * Entity -> DTO 변환 (수정 페이지 조회)
     */
    @Mapping(target = "id", source = "id.seatClassId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    EventRequest.SeatGrade toSeatGradeDto(SeatClassEntity entity);

    /**
     * DTO의 내용을 기존 엔티티 객체에 덮어씁니다.
     * ID(복합키)와 연관관계(Event)는 바뀌지 않도록 ignore 처리합니다.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    void updateEntityFromDto(EventRequest.SeatGrade dto, @MappingTarget SeatClassEntity entity);
}
