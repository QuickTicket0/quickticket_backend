package com.quickticket.quickticket.domain.ticketIssue.entity;

import com.quickticket.quickticket.domain.paymentMethod.entity.PaymentMethod;
import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    // JoinColumn의 경우는 columnDefinition을 지정하지 않는게 좋습니다
    // 참조하는 엔티티의 키 타입에 맞춰서 자동으로 타입이 정해집니다.
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long waitingNumber;

    @Column(columnDefinition = "INT UNSIGNED", nullable = false)
    private Long personNumber;

    @JdbcTypeCode(SqlTypes.JSON_ARRAY)
    @Column(columnDefinition = "JSON", nullable = false)
    private String[] seatsId;
}
