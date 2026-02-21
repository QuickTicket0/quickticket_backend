package com.quickticket.quickticket.domain.seat.service;

import com.quickticket.quickticket.domain.event.dto.EventRequest;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatClassId;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import com.quickticket.quickticket.domain.seat.mapper.SeatClassMapper;
import com.quickticket.quickticket.domain.seat.mapper.SeatMapper;
import com.quickticket.quickticket.domain.seat.repository.SeatAreaRepository;
import com.quickticket.quickticket.domain.seat.repository.SeatClassRepository;
import com.quickticket.quickticket.domain.seat.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    private final SeatRepository seatRepository;
    private final SeatClassRepository seatClassRepository;
    private final SeatAreaRepository seatAreaRepository;
    private final SeatMapper seatMapper;
    private final SeatClassMapper seatClassMapper;
    private final EventRepository eventRepository;

    public Map<Long, SeatClass> getSeatClassesMapByEventId(Long eventId) {
        return seatClassRepository.getByEvent_EventId(eventId).stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatClassId(),
                        seatClassMapper::toDomain
                ));
    }

    public List<SeatClass> getSeatClassesByEventId(Long eventId) {
        return seatClassRepository.getByEvent_EventId(eventId).stream()
                .map(seatClassMapper::toDomain)
                .toList();
    }

    public Map<Long, Seat> getSeatsMapByPerformanceId(Long performanceId) {
        return seatRepository.getByPerformance_PerformanceId(performanceId).stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatId(),
                        seatMapper::toDomain
                ));
    }

    public Seat getDomainById(Long seatId, Long performanceId) {
        var seatEntity = seatRepository.getReferenceById(new SeatId(seatId, performanceId));

        return seatMapper.toDomain(seatEntity);
    }

    @Transactional
    public Seat saveDomain(Seat domain) {

        return seatRepository.saveDomain(domain);
    }

    /**
     * 공연 등록 시 입력한 좌석 등급(VIP석, S석 등)들을 한꺼번에 저장
     * 콘서트 안에서 각 좌석 등급이 겹치지 않게 1번부터 차례대로 번호를 매김
     * @param eventId    좌석들이 속할 콘서트의 ID
     * @param seatGrades 화면에서 넘어온 좌석 이름과 가격 리스트
     */
    @Transactional
    public void saveSeatClasses(Long eventId, List<EventRequest.SeatGrade> seatGrades) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<SeatClassEntity> entities = new ArrayList<>();

        for (int i = 0; i < seatGrades.size(); i++) {
            Long seatClassId = (long) (i + 1);
            SeatClassId complexId = new SeatClassId(seatClassId, eventId);

            SeatClassEntity entity = seatClassMapper.toEntity(seatGrades.get(i), eventEntity, complexId);
            entities.add(entity);
        }

        seatClassRepository.saveAll(entities);
    }

    public List<EventRequest.SeatGrade> getSeatGradesByEventId(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new RuntimeException("Event not found");
        }

        return seatClassRepository.getByEvent_EventId(eventId).stream()
                .map(seatClassMapper::toSeatGradeDto) // 매퍼 사용
                .toList();
    }

    /**
     * 특정 이벤트의 좌석 등급 정보를 업데이트합니다.
     *
     * @param eventId  업데이트할 대상 콘서트의 아이디
     * @param seatDtos 화면에서 넘어온 수정된 좌석 등급 정보 리스트
     * @throws RuntimeException 이벤트 엔티티를 찾을 수 없는 경우 발생
     */
    @Transactional
    public void updateSeatClasses(Long eventId, List<EventRequest.SeatGrade> seatDtos) {
        EventEntity eventEntity = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<SeatClassEntity> existingEntities = seatClassRepository.getByEvent_EventId(eventId);
        Map<Long, SeatClassEntity> existingMap = existingEntities.stream()
                .collect(Collectors.toMap(e -> e.getId().getSeatClassId(), e -> e));

        List<SeatClassEntity> toSave = new ArrayList<>();
        List<Long> currentDtoIds = new ArrayList<>();

        // 업데이트
        for (EventRequest.SeatGrade dto : seatDtos) {
            if (dto.id() != null && existingMap.containsKey(dto.id())) {
                // [Update]
                SeatClassEntity existing = existingMap.get(dto.id());
                seatClassMapper.updateEntityFromDto(dto, existing);
                toSave.add(existing);
                currentDtoIds.add(dto.id());
            } else {
                Long newId = findNextId(existingEntities, toSave);
                SeatClassId complexId = new SeatClassId(newId, eventId);
                toSave.add(seatClassMapper.toEntity(dto, eventEntity, complexId));
            }
        }

        // 리스트에 없는 것들을 삭제함
        List<SeatClassEntity> toDelete = existingEntities.stream()
                .filter(e -> !currentDtoIds.contains(e.getId().getSeatClassId()))
                .collect(Collectors.toList());

        if (!toDelete.isEmpty()) {
            seatClassRepository.deleteAll(toDelete);
            // 삭제를 먼저 DB에 반영해서 ID 충돌이 되지 않도록함
            seatClassRepository.flush();
        }

        // 저장
        seatClassRepository.saveAll(toSave);
    }

    // 다음 가용 ID를 찾는 메서드
    private Long findNextId(List<SeatClassEntity> existing, List<SeatClassEntity> toSave) {
        long maxId = 0;
        for (SeatClassEntity e : existing) maxId = Math.max(maxId, e.getId().getSeatClassId());
        for (SeatClassEntity e : toSave) maxId = Math.max(maxId, e.getId().getSeatClassId());
        return maxId + 1;
    }
}
