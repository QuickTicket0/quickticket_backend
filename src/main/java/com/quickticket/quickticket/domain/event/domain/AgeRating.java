package com.quickticket.quickticket.domain.event.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgeRating implements OrdinalEnum {
    ALL(0, "전체 관람가"),
    SEVEN(1, "7세 이상"),
    FIFTEEN(2, "15세 이상"),
    ADULT(3, "청소년 관람불가");

    private final int code;
    private final String description;
}
