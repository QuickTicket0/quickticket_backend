package com.quickticket.quickticket.domain.ticketIssue.entity;

import com.quickticket.quickticket.domain.paymentMethod.entity.PaymentMethod;
import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "TICKET_ISSUE")
@IdClass(TicketIssueId.class)
@Getter
@Setter
public class TIcketIssue {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "performance_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", columnDefinition = "INT UNSIGNED", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long waitingNumber;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private long personNumber;

    @JdbcTypeCode(SqlTypes.JSON_ARRAY)
    @Column(columnDefinition = "JSON", nullable = false)
    private String[] seatsId;
}
