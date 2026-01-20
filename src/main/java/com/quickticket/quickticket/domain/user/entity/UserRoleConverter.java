package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.domain.user.domain.UserRole;
import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter extends AbstractOrdinalEnumConverter<UserRole> {
    public UserRoleConverter() {
        super(UserRole.class);
    }
}