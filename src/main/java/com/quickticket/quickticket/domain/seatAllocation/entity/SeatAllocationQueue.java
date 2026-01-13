package com.quickticket.quickticket.domain.seatAllocation.entity;

import com.quickticket.quickticket.domain.performance.entity.Performance;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT_ALLOCATION_QUEUE")
@IdClass(SeatAllocationQueueId.class)
@Getter
@Setter
public class SeatAllocationQueue {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Id
    @NotNull
    @Column(nullable = false)
    private Integer seatId;

    @NotNull
    @Column(nullable = false)
    private Long currentWaitingNumber;

    @NotNull
    @Column(nullable = false)
    private Long waitingLength;
}
