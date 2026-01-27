package com.quickticket.quickticket.domain.user.mapper;

import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true)
)
public interface UserMapper {
    User toDomain(UserEntity entity);
    UserEntity toEntity(User entity);
}
