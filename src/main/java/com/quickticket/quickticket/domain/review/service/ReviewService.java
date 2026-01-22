package com.quickticket.quickticket.domain.review.service;

import com.quickticket.quickticket.domain.review.dto.ReviewResponse;
import com.quickticket.quickticket.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;

    public List<ReviewResponse.EventPageReviewListItem> getEventResponseByEventId(Long eventId) {
        return repository.getByEvent_EventId(eventId).stream()
                .map(ReviewResponse.EventPageReviewListItem::from)
                .collect(Collectors.toList());
    }
}
