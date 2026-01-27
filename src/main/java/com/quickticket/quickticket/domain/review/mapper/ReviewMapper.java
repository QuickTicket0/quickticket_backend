package com.quickticket.quickticket.domain.review.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.review.domain.Review;
import com.quickticket.quickticket.domain.review.entity.ReviewEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    uses = {
        EventMapper.class,
        UserMapper.class
    }
)
public interface ReviewMapper {
    @Mapping(target = "id", source = "reviewId")
    @Mapping(target = "userRating", expression = "java(entity.getUserRating().floatValue() / 2)")
    Review toDomain(ReviewEntity entity);

    @Mapping(target = "reviewId", source = "id")
    @Mapping(target = "userRating", expression = "java((short) (domain.getUserRating() * 2))")
    ReviewEntity toEntity(Review domain);
}
