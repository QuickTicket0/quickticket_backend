package com.quickticket.quickticket.domain.event.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgeRating implements OrdinalEnum {
    ALL(0),
    SEVEN(1),
    FIFTEEN(2),
    ADULT(3);

    private final int code;
}