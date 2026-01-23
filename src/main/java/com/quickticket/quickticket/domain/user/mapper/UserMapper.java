package com.quickticket.quickticket.domain.user.mapper;

import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity entity);
}
