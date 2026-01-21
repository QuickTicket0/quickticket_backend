package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Entity
@Table(name = "EVENT")
@Getter
@Setter
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long eventId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private LocationEntity location;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private CategoryEntity category1;

    @ManyToOne
    @JoinColumn
    private CategoryEntity category2;

    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(length = 8000, nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    private AgeRating ageRating;

    @NotNull
    @Column(nullable = false)
    private Long userRatingSum;

    @NotNull
    @Column(nullable = false)
    private Blob thumbnailImage;

    @Column(length = 30)
    private String agentName;

    @Column(length = 30)
    private String hostName;

    @Column(length = 30)
    private String contactData;
}
