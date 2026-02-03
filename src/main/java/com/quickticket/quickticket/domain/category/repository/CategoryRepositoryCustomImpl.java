package com.quickticket.quickticket.domain.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.category.dto.CategoryCommonDto;
import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import com.quickticket.quickticket.domain.category.entity.QCategoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * category ID로 조회하여 CategoryCommonDto 변환하여 반환
     * @param categoryId 조회할 category PK
     * @return 조회된 category 정보를 담은 CategoryCommonDto (없을 경우엔 null로 반환)
     */
    public CategoryCommonDto getCommonDtoById(Long categoryId) {
        var category = QCategoryEntity.categoryEntity;

        var query = Optional.ofNullable(queryFactory
                .select(category)
                .from(category)
                .where(category.categoryId.eq(categoryId))
                .fetchOne()
            )
            .map(CategoryCommonDto::from)
            .orElse(null);

        return query;
    }
}
