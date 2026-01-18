package com.quickticket.quickticket.domain.performance.entity;

import com.quickticket.quickticket.domain.event.entity.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "PERFORMANCE")
@Getter
@Setter
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long performanceId;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Event event;

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
    private Integer runningTimeMinute;

    @NotNull
    @Column(nullable = false)
    private Integer ticket_waiting_length;
}
