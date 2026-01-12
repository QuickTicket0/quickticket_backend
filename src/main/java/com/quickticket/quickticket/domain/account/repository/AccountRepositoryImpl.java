package com.quickticket.quickticket.domain.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {
    private final JPAQueryFactory queryFactory;
}
