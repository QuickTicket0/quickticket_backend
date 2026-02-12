package com.quickticket.quickticket.domain.account.domain;

import com.quickticket.quickticket.shared.converters.OrdinalEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum AccountType implements OrdinalEnum {
    USER(0, "USER"),
    ADMIN(1, "ADMIN");

    @Getter
    private final int code;
    private final String name;

    public String getAuthority() {
        return "ROLE_"+this.name;
    }

    public String getRole() {
        return this.name;
    }
}