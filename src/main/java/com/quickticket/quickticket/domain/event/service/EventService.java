package com.quickticket.quickticket.domain.event.service;

import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import com.quickticket.quickticket.domain.category.repository.CategoryRepository;
import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.dto.EventRequest;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.domain.location.mapper.LocationMapper;
import com.quickticket.quickticket.domain.location.repository.LocationRepository;
import com.quickticket.quickticket.domain.performance.dto.PerformanceRequest;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.performance.repository.PerformanceRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import com.quickticket.quickticket.shared.infra.aws.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;
    private final PerformanceRepository performanceRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    private final S3Service s3Service;

    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;
    
    public List<Event> getHotEventsTopN(int topN) {
        return eventRepository.getAllByOrderByViewsDesc(PageRequest.of(0, topN)).stream()
                .map(eventMapper::toDomain)
                .toList();
    }

    public EventResponse.Details getResponseDetailsById(Long id) {
        return EventResponse.Details.from(
                eventRepository.getEntityByEventId(id)
                    .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND))
        );
    }

    public EventResponse.ListItem getResponseListItemById(Long id) {
        EventEntity event = eventRepository.getEntityByEventId(id)
                .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND));
        PerformanceEntity performance = performanceRepository.findAll().stream()
                .filter(p -> p.getEvent().getEventId().equals(id))
                .findFirst()
                .orElse(null);

        return EventResponse.ListItem.from(event, performance);
    }

    /**
     * 관리자 페이지용 공연 목록을 조회
     * DB에 저장된 모든 공연(Performance) 데이터를 가져와 관리자용 DTO 리스트로 변환하여 반환
     * @return 관리자페이지의 공연 목록 리스트 (EventResponse.ListItem)
     */
    public List<EventResponse.ListItem> getEventListForAdmin() {
        return performanceRepository.findAll().stream()
                .map(performance -> EventResponse.ListItem.from(performance.getEvent(), performance))
                .toList();
    }

    /**
     * 새로운 콘서트 정보를 등록하고 썸네일 이미지 저장
     * @param eventDto 공연 이름, 날짜, 장소 정보 등이 담긴 데이터 객체
     * @param file     사용자가 업로드한 포스터 이미지 파일
     * @return         DB에 저장된 eventId
     */
    @Transactional
    public Long saveEvent(EventRequest.Create eventDto, MultipartFile file) {
        // 카테고리 조회
        CategoryEntity category1 = categoryRepository.findById(eventDto.category1Id())
                .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND));

        // LocationEntity 생성 및 저장
        LocationEntity locationEntity = locationMapper.toEntity(eventDto.location());
        LocationEntity savedLocation = locationRepository.save(locationEntity);

        // 완성된 장소 객체와 함께 이벤트 저장
        EventEntity eventEntity = eventMapper.toEntity(eventDto, savedLocation, category1);
        EventEntity savedEvent = eventRepository.save(eventEntity);

        // S3 업로드
        if (file != null && !file.isEmpty()) {
            s3Service.uploadId(file, "images/event", savedEvent.getEventId().toString());
        }

        return savedEvent.getEventId();
    }
}
