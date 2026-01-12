package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.domain.user.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toAccount(UserDto userDto);
    UserDto toAccountDto(User account);
}
