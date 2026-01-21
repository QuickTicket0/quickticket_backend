package com.quickticket.quickticket.domain.ticket.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quickticket.quickticket.domain.event.entity.QEventEntity;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.entity.QSeatPaymentIssueEntity;
import com.quickticket.quickticket.domain.performance.entity.QPerformanceEntity;
import com.quickticket.quickticket.domain.seat.entity.*;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.entity.QTicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.entity.QWantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.WantingSeatsEntity;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class TicketIssueRepositoryImpl implements TicketIssueRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public TicketResponse.Details getResponseDetailsById(Long id) {
        var ticket = QTicketIssueEntity.ticketIssueEntity;
        var seat = QSeatEntity.seatEntity;
        var seatClass = QSeatClassEntity.seatClassEntity;
        var seatPaymentIssue = QSeatPaymentIssueEntity.seatPaymentIssueEntity;
        var performance = QPerformanceEntity.performanceEntity;
        var event = QEventEntity.eventEntity;
        var wantingSeat = QWantingSeatsEntity.wantingSeatsEntity;

        Tuple ticketQuery = Optional.ofNullable(queryFactory
                .select(ticket, performance, event)
                .from(ticket)
                .join(ticket.performance, performance)
                .join(performance.event, event)
                .where(ticket.ticketIssueId.eq(id))
                .fetchOne()).orElseThrow();

        var seatsQuery = Optional.ofNullable(queryFactory
                .selectFrom(seat)
                .where(
                        seat.performance.eq(
                        ticketQuery.get(performance)))
                .fetch()).orElseThrow();

        Map<Long, TicketResponse.SeatInfo> seats = seatsQuery.stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatId(),
                        TicketResponse.SeatInfo::from
                ));

        var seatClassesQuery = Optional.ofNullable(queryFactory
                .selectFrom(seatClass)
                .where(seatClass.event.eq(ticketQuery.get(event)))
                .fetch()).orElseThrow();

        Map<Long, TicketResponse.SeatClassInfo> seatClasses = seatClassesQuery.stream()
                .collect(Collectors.toMap(
                        e -> e.getId().getSeatClassId(),
                        TicketResponse.SeatClassInfo::from
                ));

        List<Long> wantingSeatsIdQuery = Optional.ofNullable(queryFactory
                .select(wantingSeat.id.seatId.seatId)
                .from(wantingSeat)
                .where(
                        wantingSeat.ticketIssue.eq(
                                ticketQuery.get(ticket))
                )
                .fetch()).orElseThrow();

        var ticketedSeatClasses = wantingSeatsIdQuery.stream()
                .map(seatId -> seats.get(seatId).seatClassId())
                .distinct()
                .map(seatId -> seatClasses.get(seatId))
                .collect(Collectors.toList());

        var seatPaymentIssuesQuery = Optional.ofNullable(queryFactory
                .selectFrom(seatPaymentIssue)
                .where(
                        seatPaymentIssue.ticketIssue.eq(
                        ticketQuery.get(ticket))
                )
                .fetch()).orElseThrow();

        var seatPayments = seatPaymentIssuesQuery.stream()
                .map(TicketResponse.SeatPaymentInfo::from)
                .collect(Collectors.toCollection(ArrayList::new));

        return TicketResponse.Details.builder()
                .id(ticketQuery.get(ticket.ticketIssueId))
                .status(ticketQuery.get(ticket.status))
                .paymentMethod(PaymentMethodCommonDto.from(
                        ticketQuery.get(ticket.paymentMethod)
                ))
                .waitingNumber(ticketQuery.get(ticket.waitingNumber))
                .personNumber(ticketQuery.get(ticket.personNumber))
                .createdAt(ticketQuery.get(ticket.createdAt))
                .canceledAt(ticketQuery.get(ticket.canceledAt))
                .event(TicketResponse.EventInfo.from(
                        ticketQuery.get(event)
                ))
                .performance(TicketResponse.PerformanceInfo.from(
                        ticketQuery.get(performance)
                ))
                .seats(seats)
                .seatPayments(seatPayments)
                .seatClasses(ticketedSeatClasses)
                .wantingSeatsId(wantingSeatsIdQuery)
                .build();
    }
}
