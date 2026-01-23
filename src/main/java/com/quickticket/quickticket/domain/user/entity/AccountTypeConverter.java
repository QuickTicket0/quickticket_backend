package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.domain.account.domain.AccountType;
import com.quickticket.quickticket.shared.converters.AbstractOrdinalEnumConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AccountTypeConverter extends AbstractOrdinalEnumConverter<AccountType> {
    public AccountTypeConverter() {
        super(AccountType.class);
    }
}