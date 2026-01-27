package com.quickticket.quickticket.domain.category.mapper;

import com.quickticket.quickticket.domain.category.domain.Category;
import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    builder = @Builder(disableBuilder = true)
)
public interface CategoryMapper {
    @Mapping(target = "id", source = "categoryId")
    Category toDomain(CategoryEntity entity);
}
