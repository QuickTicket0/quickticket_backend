package com.quickticket.quickticket.domain.seatAllocation.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class SeatAllocationQueueId implements Serializable {
    private Integer seatId;
    private Long performance;
}
