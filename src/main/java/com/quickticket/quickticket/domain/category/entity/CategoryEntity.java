package com.quickticket.quickticket.domain.category.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "CATEGORY")
@Getter
@EntityListeners(CategoryCacheListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long categoryId;

    @NotNull
    @Column(length = 20, nullable = false)
    private String name;
}
