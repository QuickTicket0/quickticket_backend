package com.quickticket.quickticket.domain.performance.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "PERFORMANCE")
@Getter
@Setter
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long performanceId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;

    @Column(columnDefinition = "TINYINT UNSIGNED", nullable = false)
    private short performanceNth;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long targetSeatNumber;

    @JdbcTypeCode(SqlTypes.JSON_ARRAY)
    @Column(columnDefinition = "JSON", nullable = false)
    private String[] performersName;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime ticketingStartsAt;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime ticketingEndsAt;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime performanceStartsAt;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long runningTimeMinute;
}
