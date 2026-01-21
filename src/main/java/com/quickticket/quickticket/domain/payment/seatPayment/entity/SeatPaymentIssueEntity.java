package com.quickticket.quickticket.domain.payment.seatPayment.entity;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssueStatus;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "SEAT_PAYMENT_ISSUE")
@Getter
@Setter
public class SeatPaymentIssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "ticket_issue_id", nullable = false)
    private TicketIssueEntity ticketIssue;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @NotNull
    @JoinColumns({
            @JoinColumn(name = "seat_id", referencedColumnName = "seat_id",
                    nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "performance_id", referencedColumnName = "performance_id",
                    nullable = false, insertable = false, updatable = false)
    })
    private SeatEntity seat;

    @NotNull
    @Column(nullable = false)
    private SeatPaymentIssueStatus status;

    @NotNull
    @Column(nullable = false)
    private Long amount;

    @NotNull
    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
