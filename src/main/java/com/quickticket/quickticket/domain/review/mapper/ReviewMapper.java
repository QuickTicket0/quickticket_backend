package com.quickticket.quickticket.domain.review.mapper;

import com.quickticket.quickticket.domain.event.mapper.EventMapper;
import com.quickticket.quickticket.domain.review.domain.Review;
import com.quickticket.quickticket.domain.review.entity.ReviewEntity;
import com.quickticket.quickticket.domain.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        EventMapper.class,
        UserMapper.class
})
public interface ReviewMapper {
    @Mapping(target = "id", source = "reviewId")
    @Mapping(target = "userRating", source = "userRating", expression = "java(userRating.floatValue() / 2)")
    Review toDomain(ReviewEntity entity);

    @Mapping(target = "reviewId", source = "id")
    @Mapping(target = "userRating", source = "userRating", expression = "java((Short) (userRating * 2))")
    ReviewEntity toEntity(Review domain);
}
