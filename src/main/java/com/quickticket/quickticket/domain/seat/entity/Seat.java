package com.quickticket.quickticket.domain.seat.entity;

import com.quickticket.quickticket.domain.performance.entity.Performance;
import com.quickticket.quickticket.domain.event.entity.Event;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// DB의 복합 primary key는 JPA에서 @IdClass 혹은 @EmbeddedId로 구현할 수 있습니다
// @EmbeddedId는 키로 작용하는 필드들의 묶음 객체로 나타내서 다룰 필요가 있을 때,
// @IdClass는 복합 키 묶음을 객체로 표현해서 지목하는 용도는 필요없고, 관련된 여러 필드를 각각
// 다룰 일이 있을때 사용합니다.
@Entity
@Table(name = "SEAT")
@Getter
@Setter
public class Seat {
    @EmbeddedId
    private SeatId id;

    @MapsId("performanceId")
    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @ManyToOne
    @NotNull
    @JoinColumns({
            @JoinColumn(name = "seat_class_id", referencedColumnName = "seat_class_id",
                    nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "event_id", referencedColumnName = "event_id",
                    nullable = false, insertable = false, updatable = false)
    })
    private SeatClass seatClass;

    @ManyToOne
    @NotNull
    @JoinColumns({
            @JoinColumn(name = "seat_area_id", referencedColumnName = "seat_area_id",
                    nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "event_id", referencedColumnName = "event_id",
                    nullable = false, insertable = false, updatable = false)
    })
    private SeatArea area;


    private Long currentWaitingNumber;
}
