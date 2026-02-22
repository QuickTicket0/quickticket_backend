package com.quickticket.quickticket.domain.event.entity;

import com.quickticket.quickticket.domain.category.entity.CategoryEntity;
import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.location.entity.LocationEntity;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EVENT")
@Getter
@Setter
@EntityListeners(EventCacheListener.class)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventEntity {
    // 회차(Performance)와 연결(cascade로 삭제하기 위해 연결을함)
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerformanceEntity> performances = new ArrayList<>();

    // 좌석 등급(SeatClass)과 연결
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatClassEntity> seatClasses = new ArrayList<>();


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long eventId;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_location_id")
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

    @Column(length = 30)
    private String agentName;

    @Column(length = 30)
    private String hostName;

    @Column(length = 30)
    private String contactData;

    @NotNull
    @Column(nullable = false)
    private Long views = 0L;
}
