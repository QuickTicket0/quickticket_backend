package com.quickticket.quickticket.domain.account.entity;

import com.quickticket.quickticket.domain.account.dto.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toAccount(AccountDto accountDto);
    AccountDto toAccountDto(Account account);
}
