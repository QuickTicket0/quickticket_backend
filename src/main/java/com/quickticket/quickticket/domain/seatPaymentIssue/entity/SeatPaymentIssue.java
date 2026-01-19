package com.quickticket.quickticket.domain.seatPaymentIssue.entity;

import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.seat.entity.Seat;
import com.quickticket.quickticket.domain.ticketIssue.entity.TicketIssue;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SEAT_PAYMENT_ISSUE")
@Getter
@Setter
public class SeatPaymentIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ticket_issue_id", nullable = false)
    private TicketIssue ticketissue;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @NotNull
    @JoinColumns({
            @JoinColumn(name = "seat_id", referencedColumnName = "seat_id",
                    nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "performance_id", referencedColumnName = "performance_id",
                    nullable = false, insertable = false, updatable = false)
    })
    private Seat seat;

    @NotNull
    @Column(nullable = false)
    private SeatPaymentIssueStatus status;
}
