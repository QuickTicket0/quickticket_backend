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
    private final PerformanceService performanceService;
    private final UserService userService;
    private final PaymentMethodService paymentMethodService;
    private final TicketIssueRepository ticketIssueRepository;
    private final WantingSeatsRepositoryCustom wantingSeatsRepository;
    private final SeatResponseMapper seatMapper;
    private final SeatPaymentIssueResponseMapper seatPaymentIssueMapper;
    private final SeatClassResponseMapper seatClassMapper;
    private final PerformanceResponseMapper performanceMapper;
    private final EventResponseMapper eventMapper;
    private final TicketIssueMapper ticketIssueMapper;
    
    public Ticket presetTicket(TicketRequest.Preset dto, Long userId) {
        var wantingSeats = dto.wantingSeatsId.stream()
                .map((id) -> seatService.findSeatById(id))
                .toList();
        
        var newTicket = Ticket.builder()
                .performance(performanceService.findPerformanceById(dto.performanceId))
                .user(userService.findUserById(userId))
                .paymentMethod(paymentMethodService.findPaymentMethodById(dto.paymentMethodId))
                .personNumber(dto.personNumber)
                .wantingSeats(wantingSeats)
                .status(TicketStatus.PRESET)
                .build();

        newTicket = ticketIssueMapper.toDomain(ticketIssueRepository.save(ticketIssueMapper.toEntity(newTicket)));

        return newTicket;
    }

    // redis 캐싱 필요
    public Long getCurrentWaitingLengthOfPerformance(Long performanceId) {
        return ticketIssueRepositoryCustom.getMaxWaitingNumberOfPerformance(performanceId);
    }

    @Transactional
    public Ticket createNewTicket(TicketRequest.Ticket dto, Long userId) {
        var waitingNumber = this.getCurrentWaitingLengthOfPerformance(dto.performanceId) + 1;
        
        var wantingSeats = dto.wantingSeatsId.stream()
                .map((id) -> seatService.findSeatById(id))
                .collect(Collector.toList());
        
        var newTicket = Ticket.builder()
                .performance(performanceService.findPerformanceById(dto.performanceId))
                .user(userService.findUserById(userId))
                .paymentMethod(paymentMethodService.findPaymentMethodById(dto.paymentMethodId))
                .personNumber(dto.personNumber)
                .waitingNumber(waitingNumber)
                .wantingSeats(wantingSeats)
                .status(TicketStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        newTicket = ticketIssueMapper.toDomain(ticketIssueRepository.save(ticketIssueMapper.toEntity(newTicket)));

        return newTicket;
    }

    public TicketResponse.Details getResponseDetailsById(Long ticketId) {
        var ticketEntity = ticketIssueRepository.getById(ticketId);
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
                .toList();

        var seatPayments = seatPaymentService.getSeatPaymentsByTicketIssueId(ticketId).stream()
                .map(seatPaymentIssueMapper::toResponse)
                .toList();

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
