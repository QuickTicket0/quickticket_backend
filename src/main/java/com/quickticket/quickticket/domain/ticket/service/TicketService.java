package com.quickticket.quickticket.domain.ticket.service;

import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.method.service.PaymentMethodService;
import com.quickticket.quickticket.domain.payment.seatPayment.service.SeatPaymentService;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.service.SeatService;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.mapper.*;
import com.quickticket.quickticket.domain.ticket.repository.TicketIssueRepository;
import com.quickticket.quickticket.domain.ticket.repository.WantingSeatsRepositoryCustom;
import com.quickticket.quickticket.domain.user.service.UserService;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Ticket presetTicket(TicketRequest.Preset dto, Long userId) {
        var wantingSeats = dto.wantingSeatsId().stream()
                .map((id) -> seatService.getDomainById(id, dto.performanceId()))
                .collect(Collectors.toMap(
                        Seat::getId,
                        seat -> seat
                ));

        var newTicket = Ticket.builder()
                .performance(performanceService.getDomainById(dto.performanceId()))
                .user(userService.getDomainById(userId))
                .paymentMethod(paymentMethodService.getDomainById(dto.paymentMethodId()))
                .personNumber(dto.personNumber())
                .wantingSeats(wantingSeats)
                .status(TicketStatus.PRESET)
                .build();
        
        // 이건 ticket에서 추가시 자동으로 대기 길이 +1 하게 만들어야함
        // performance.addCurrentWaitingLength();
        newTicket.allocateToPerformance(performance);
        newTicket = ticketIssueRepository.saveDomain(newTicket);

        return newTicket;
    }

    @Transactional
    public Ticket createNewTicket(TicketRequest.Ticket dto, Long userId) {
        var waitingNumber = ticketIssueRepository.getLastWaitingNumberOfPerformance(dto.performanceId()) + 1;

        var wantingSeats = dto.wantingSeatsId().stream()
                .map((id) -> seatService.getDomainById(id, dto.performanceId()))
                .collect(Collectors.toMap(
                        Seat::getId,
                        seat -> seat
                ));

        var newTicket = Ticket.builder()
                .performance(performanceService.getDomainById(dto.performanceId()))
                .user(userService.getDomainById(userId))
                .paymentMethod(paymentMethodService.getDomainById(dto.paymentMethodId()))
                .personNumber(dto.personNumber())
                .waitingNumber(waitingNumber)
                .wantingSeats(wantingSeats)
                .status(TicketStatus.WAITING)
                .createdAt(LocalDateTime.now())
                .build();

        newTicket = ticketIssueRepository.saveDomain(newTicket);
        return newTicket;
    }

    public TicketResponse.Details getResponseDetailsById(Long ticketId) {
        var ticketEntity = ticketIssueRepository.getReferenceById(ticketId);
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

    @Transactional
    public TicketRequest.Cancel cancelTicket(TicketRequest.Cancel dto, Long userId) {
        Ticket ticket = ticketIssueRepository.getDomainById(dto.id());

        if (!ticket.getUser().getId().equals(userId)) {
            throw new DomainException(TicketErrorCode.USER_NOT_EQUAL);
        }
        if (ticket.getStatus() == TicketStatus.CANCELED) {
            throw new DomainException(TicketErrorCode.CANCELED_ALREADY);
        }
        ticket.cancel();
        // ticket.cancel() 메서드 안에서 호출해야할듯
        // this.allocateSeatsToNextTickets(ticket);
        ticketIssueRepository.saveDomain(ticket);
        
        return dto;
    }

    // private void allocateSeatsToNextTickets(Ticket ticket) {
    //     var waitingNth = ticket.getWaitingNumber() + 1;
    //     var waitingLength = ticket.getPerformance().getWaitingLength();
    //     var performanceId = ticket.getPerformance().getId();
    //     var seatId = 0;
        
    //     while (waitingNth <= waitingLength) {
    //         if (wantingSeatsRepository.doesWaitingNthWantsTheSeat(waitingNth, performanceId, seatId)) {
    //             break;
    //         }
            
    //         waitingNth++;
    // // 도메인객체 변하면 saveDomain() 호출하기
    //     }
    // }
}
