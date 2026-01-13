package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole implements OrdinalEnum {
    USER(0),
    ADMIN(1);

    private final int code;
}
