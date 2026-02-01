package com.quickticket.quickticket.domain.category.repository;

import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;

public interface CategoryRepositoryCustom {
    CategoryCommonDto getCategoryById(Long categoryId);
}
