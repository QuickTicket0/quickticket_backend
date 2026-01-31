package com.quickticket.quickticket.domain.review.service;

import com.quickticket.quickticket.domain.review.domain.Review;
import com.quickticket.quickticket.domain.review.dto.ReviewResponse;
import com.quickticket.quickticket.domain.review.mapper.ReviewMapper;
import com.quickticket.quickticket.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository repository;
    private final ReviewMapper mapper;

    public List<ReviewResponse.EventPageReviewListItem> getEventResponseByEventId(Long eventId) {
        return repository.getByEvent_EventId(eventId).stream()
                .map(ReviewResponse.EventPageReviewListItem::from)
                .toList();
    }

    public List<Review> getReviewsByEventId(Long eventId) {
        return repository.getByEvent_EventId(eventId).stream()
            .map(mapper::toDomain)
            .toList();
    }
}
