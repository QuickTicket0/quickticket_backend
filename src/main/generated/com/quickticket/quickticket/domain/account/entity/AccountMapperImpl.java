package com.quickticket.quickticket.domain.account.entity;

import com.quickticket.quickticket.domain.account.dto.AccountDto;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-14T12:39:20+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toAccount(AccountDto accountDto) {
        if ( accountDto == null ) {
            return null;
        }

        Account account = new Account();

        account.setId( accountDto.id() );

        return account;
    }

    @Override
    public AccountDto toAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        long id = 0L;

        id = account.getId();

        AccountDto accountDto = new AccountDto( id );

        return accountDto;
    }
}
