package com.quickticket.quickticket.domain.user.entity;

import com.quickticket.quickticket.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toUser(User user);
    User toUserDto(UserEntity account);
}
