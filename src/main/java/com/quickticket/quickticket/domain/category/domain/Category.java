package com.quickticket.quickticket.domain.category.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/// 공연을 쉽게 분류하고 찾기 위한 카테고리
@AllArgsConstructor
@Getter
public class Category {
    /// 카테고리 고유 id. Primary key로서 완전히 고유핟
    private Long id;
    /// 카테고리가 보여지는 이름 (로맨스, 스릴러, 스포츠 등)
    private String name;

    public void changeName(String newName) {
        validateName(newName);

        this.name = newName;
    }

    @Builder(builderMethodName = "create")
    public Category(String name) {
        validateName(name);

        this.name = name;
    }

    private static void validateName(String newName) {
        if (
            newName == null
            || newName.isBlank()
            || newName.length() > 20
        ) {
            throw new IllegalArgumentException();
        }
    }
}