package com.quickticket.quickticket.domain.user.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole implements OrdinalEnum {
    USER(0, "USER"),
    ADMIN(1, "ADMIN");

    private final int code;
    private final String name;
}
