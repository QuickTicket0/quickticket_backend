package com.quickticket.quickticket.domain.performance.entity;

import com.quickticket.quickticket.domain.event.entity.EventEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "PERFORMANCE")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long performanceId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private EventEntity event;

    @NotNull
    @Column(nullable = false)
    private Integer performanceNth;

    @NotNull
    @Column(nullable = false)
    private Integer targetSeatNumber;

    @JdbcTypeCode(SqlTypes.JSON_ARRAY)
    @NotNull
    @Column(nullable = false)
    private List<String> performersName;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime ticketingStartsAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime ticketingEndsAt;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime performanceStartsAt;

    @NotNull
    @Column(nullable = false)
    private LocalTime runningTime;
}
