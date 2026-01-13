package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class SeatPaymentIssueId implements Serializable {
    private Long user;
    private Integer seatId;
    private Long performance;
}
