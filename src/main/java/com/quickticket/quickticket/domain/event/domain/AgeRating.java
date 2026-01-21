package com.quickticket.quickticket.domain.event.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeRating {
    ALL("전체 관람가");

    private final String description; // 화면에 보여줄 한글 이름
}
