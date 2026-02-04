package com.quickticket.quickticket.domain.event.service;

import com.quickticket.quickticket.domain.event.domain.Event;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository repository;
    private final EventMapper mapper;
    
    public List<Event> getHotEventsTopN(int topN) {
        return repository.getAllByOrderByViewsDesc(PageRequest.of(0, topN)).stream()
                .map(mapper::toDomain)
                .toList();
    }

    public EventResponse.Details getResponseDetailsById(Long id) {
        return EventResponse.Details.from(
                repository.getEntityByEventId(id)
                    .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND))
        );
    }

    public EventResponse.ListItem getResponseListItemById(Long id) {
        return EventResponse.ListItem.from(
                repository.getEntityByEventId(id)
                    .orElseThrow(() -> new DomainException(EventErrorCode.NOT_FOUND))
        );
    }

    public List<EventResponse.ListItem> getEventListForAdmin() {
        return null;
    }
}
