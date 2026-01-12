package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole implements OrdinalEnum {
    U(0),
    A(1);

    private final int code;
}
