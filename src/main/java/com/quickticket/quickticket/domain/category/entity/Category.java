package com.quickticket.quickticket.domain.category.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long categoryId;

    @Column(columnDefinition = "VARCHAR(20)", length = 20, nullable = false)
    private String name;
}
