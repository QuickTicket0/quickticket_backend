package com.quickticket.quickticket.domain.event.service;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final EventMapper mapper;
    
    public List<Event> getHotEventsTopN(int topN) {
        return repository.getAllByOrderByViewsDesc(PageRequest.of(0, topN)).stream()
                .map(mapper::toDomain)
                .collect(Collector.toList());
    }

    public EventResponse.Details getResponseDetailsById(Long id) {
        return EventResponse.Details.from(
                repository.getById(id)
                    .orElseThrow(() -> new BusinessException(EventErrorCode.NOT_FOUND))
        );
    }

    public EventResponse.ListItem getResponseListItemById(Long id) {
        return EventResponse.ListItem.from(
                repository.getById(id)
                    .orElseThrow(() -> new BusinessException(EventErrorCode.NOT_FOUND))
        );
    }

    public List<EventResponse.ListItem> getEventListForAdmin() {
    }
}
