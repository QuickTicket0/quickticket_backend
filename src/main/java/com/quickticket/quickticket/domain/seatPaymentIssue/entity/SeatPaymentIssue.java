package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import com.quickticket.quickticket.domain.ticketIssue.entity.TicketIssueId;
import com.quickticket.quickticket.domain.seat.entity.SeatId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT_PAYMENT_ISSUE")
@IdClass(SeatPaymentIssueId.class)
@Getter
@Setter
public class SeatPaymentIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(nullable = false)
    private Long paymentId;

    @Id
    @NotNull
    @JoinColumn(nullable = false)
    private TicketIssueId ticketissueId;


    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @NotNull
    @JoinColumn(nullable = false)
    private SeatId seatId;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;


    @NotNull
    @Column(nullable = false)
    private SeatPaymentIssueState state;
}
