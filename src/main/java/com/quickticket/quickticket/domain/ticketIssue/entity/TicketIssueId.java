package com.quickticket.quickticket.domain.ticketIssue.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class TicketIssueId implements Serializable {
    private Long user;
    private Long performance;
}
