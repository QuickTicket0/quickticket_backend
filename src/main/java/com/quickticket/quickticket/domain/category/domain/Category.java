package com.quickticket.quickticket.domain.category.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

/// 공연을 쉽게 분류하고 찾기 위한 카테고리
@Builder(access = PRIVATE)
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
    
    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }

    public static Category create(String name) {
        validateName(name);

        return Category.builder()
            .name(name)
            .build();
    }

    public static Category recreate(Long id, String name) {
        return Category.builder()
            .id(id)
            .name(name)
            .build();
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