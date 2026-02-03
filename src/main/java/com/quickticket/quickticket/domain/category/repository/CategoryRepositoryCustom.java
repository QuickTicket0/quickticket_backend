package com.quickticket.quickticket.domain.category.repository;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import org.springframework.cache.annotation.Cacheable;

public interface CategoryRepositoryCustom {
    @Cacheable(value = "category_commonDto", key = "#categoryId")
    CategoryCommonDto getCommonDtoById(Long categoryId);
}
