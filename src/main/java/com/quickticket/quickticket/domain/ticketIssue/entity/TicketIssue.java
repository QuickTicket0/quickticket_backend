package com.quickticket.quickticket.domain.ticketIssue.entity;

import com.quickticket.quickticket.domain.paymentMethod.entity.PaymentMethod;
import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

// DB의 복합 primary key는 JPA에서 @IdClass 혹은 @EmbeddedId로 구현할 수 있습니다
// @EmbeddedId는 키로 작용하는 필드들의 묶음 객체로 나타내서 다룰 필요가 있을 때,
// @IdClass는 복합 키 묶음을 객체로 표현해서 지목하는 용도는 필요없고, 관련된 여러 필드를 각각
// 다룰 일이 있을때 사용합니다.
@Entity
@Table(name = "TICKET_ISSUE")
@IdClass(TicketIssueId.class)
@Getter
@Setter
public class TicketIssue {
    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @NotNull
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull
    @Column(nullable = false)
    private Long waitingNumber;

    @NotNull
    @Column(nullable = false)
    private Long personNumber;

    @JdbcTypeCode(SqlTypes.JSON_ARRAY)
    @NotNull
    @Column(nullable = false)
    private List<String> seatsId;
}
