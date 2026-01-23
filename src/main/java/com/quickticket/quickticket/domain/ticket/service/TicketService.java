package com.quickticket.quickticket.domain.ticket.service;

import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.service.SeatPaymentService;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.service.SeatService;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.mapper.*;
import com.quickticket.quickticket.domain.ticket.repository.TicketIssueRepository;
import com.quickticket.quickticket.domain.ticket.repository.WantingSeatsRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final SeatService seatService;
    private final SeatPaymentService seatPaymentService;
    private final TicketIssueRepository ticketRepository;
    private final WantingSeatsRepositoryCustom wantingSeatsRepository;
    private final SeatResponseMapper seatMapper;
    private final SeatPaymentIssueResponseMapper seatPaymentIssueMapper;
    private final SeatClassResponseMapper seatClassMapper;
    private final PerformanceResponseMapper performanceMapper;
    private final EventResponseMapper eventMapper;

    public TicketResponse.Details getResponseDetailsById(Long ticketId) {
        var ticketEntity = ticketRepository.getById(ticketId);
        var performanceEntity = ticketEntity.getPerformance();
        var eventEntity = performanceEntity.getEvent();

        Map<Long, Seat> seats = seatService.getSeatsByPerformanceId(performanceEntity.getPerformanceId());
        Map<Long, SeatClass> seatClasses = seatService.getSeatClassesByEventId(eventEntity.getEventId());

        Map<Long, TicketResponse.SeatInfo> seatInfos = seats.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> seatMapper.toResponse(e.getValue())
                ));

        var wantingSeatsId = wantingSeatsRepository.getSeatIdsByTicketIssueId(ticketId);

        var ticketedSeatClasses = wantingSeatsId.stream()
                .map(seatId -> seats.get(seatId).getSeatClass().getId())
                .distinct()
                .map(seatClasses::get)
                .map(seatClassMapper::toResponse)
                .collect(Collectors.toList());

        var seatPayments = seatPaymentService.getSeatPaymentsByTicketIssueId(ticketId).stream()
                .map(seatPaymentIssueMapper::toResponse)
                .collect(Collectors.toList());

        return TicketResponse.Details.builder()
                .id(ticketEntity.getTicketIssueId())
                .status(ticketEntity.getStatus())
                .paymentMethod(PaymentMethodCommonDto.from(
                        ticketEntity.getPaymentMethod()
                ))
                .waitingNumber(ticketEntity.getWaitingNumber())
                .personNumber(ticketEntity.getPersonNumber())
                .createdAt(ticketEntity.getCreatedAt())
                .canceledAt(ticketEntity.getCanceledAt())
                .event(eventMapper.toResponse(
                        eventEntity
                ))
                .performance(performanceMapper.toResponse(
                        performanceEntity
                ))
                .seats(seatInfos)
                .seatPayments(seatPayments)
                .seatClasses(ticketedSeatClasses)
                .wantingSeatsId(wantingSeatsId)
                .build();
    }
}
