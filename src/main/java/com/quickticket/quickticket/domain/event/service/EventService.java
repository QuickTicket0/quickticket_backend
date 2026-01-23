package com.quickticket.quickticket.domain.event.service;

import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;

    public EventResponse.Details getResponseDetailsById(Long id) {
        return EventResponse.Details.from(
                repository.getById(id)
        );
    }

    public EventResponse.ListItem getResponseListItemById(Long id) {
        return EventResponse.ListItem.from(
                repository.getById(id)
        );
    }
}
