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
import com.quickticket.quickticket.domain.ticket.repository.WantingSeatsRepository;
import com.quickticket.quickticket.domain.user.service.UserService;
import com.quickticket.quickticket.shared.aspects.DistributedLock;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketService {
    private final SeatService seatService;
    private final SeatPaymentService seatPaymentService;
    private final PerformanceService performanceService;
    private final UserService userService;
    private final PaymentMethodService paymentMethodService;

    private final TicketIssueRepository ticketIssueRepository;
    private final WantingSeatsRepository wantingSeatsRepository;

    private final SeatResponseMapper seatMapper;
    private final SeatPaymentIssueResponseMapper seatPaymentIssueMapper;
    private final SeatClassResponseMapper seatClassMapper;
    private final PerformanceResponseMapper performanceMapper;
    private final EventResponseMapper eventMapper;

    public List<TicketResponse.ListItem> getMyTickets(Long userId) {
        return ticketIssueRepository.getAllByUser_Id(userId).stream()
                .map(e -> TicketResponse.ListItem.builder()
                        .id(e.getTicketIssueId())
                        .createdAt(e.getCreatedAt())
                        .performanceStartsAt(e.getPerformance().getPerformanceStartsAt())
                        .eventName(e.getPerformance().getEvent().getName())
                        .personNumber(e.getPersonNumber())
                        .build()
                )
                .toList();
    }

    @Transactional
    public Ticket presetTicket(TicketRequest.Preset dto, Long userId) {
        var wantingSeats = dto.wantingSeatsId().stream()
                .map((id) -> seatService.getDomainById(id, dto.performanceId()))
                .collect(Collectors.toMap(
                        Seat::getId,
                        seat -> seat
                ));

        var newTicket = Ticket.builder()
                .user(userService.getDomainById(userId))
                .paymentMethod(paymentMethodService.getDomainById(dto.paymentMethodId()))
                .personNumber(dto.personNumber())
                .wantingSeats(wantingSeats)
                .status(TicketStatus.PRESET)
                .build();

        newTicket = ticketIssueRepository.saveDomain(newTicket);
        return newTicket;
    }

    @Transactional
    @DistributedLock(key = "performance#dto.performanceId()")
    public Ticket createNewTicket(TicketRequest.Ticket dto, Long userId) {
        var waitingNumber = performanceService.getWaitingLengthOfPerformance(dto.performanceId()) + 1;
        var wantingSeats = dto.wantingSeatsId().stream()
                .map((id) -> seatService.getDomainById(id, dto.performanceId()))
                .collect(Collectors.toMap(
                        Seat::getId,
                        seat -> seat
                ));

        var performance = performanceService.getDomainById(dto.performanceId());
        var newTicket = Ticket.builder()
                .user(userService.getDomainById(userId))
                .paymentMethod(paymentMethodService.getDomainById(dto.paymentMethodId()))
                .personNumber(dto.personNumber())
                .waitingNumber(waitingNumber)
                .wantingSeats(wantingSeats)
                .build();

        // 비어있는 좌석 있을시 바로 배정
        newTicket.ticketToPerformance(performance);

        newTicket = ticketIssueRepository.saveDomain(newTicket);
        performance = performanceService.saveDomain(performance);
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
    @DistributedLock(key = "performance#dto.performanceId()")
    public Ticket cancelTicket(TicketRequest.Cancel dto, Long userId) {
        Ticket ticket = ticketIssueRepository.getDomainById(dto.id());

        if (!ticket.getUser().getId().equals(userId)) {
            throw new DomainException(TicketErrorCode.USER_NOT_EQUAL);
        }
        if (ticket.getStatus() == TicketStatus.CANCELED) {
            throw new DomainException(TicketErrorCode.CANCELED_ALREADY);
        }
        if (ticket.getStatus() == TicketStatus.PRESET) {
            // throw new DomainException(TicketErrorCode.NOT_ALLOCATED);
        }

        ticket.cancel();

        if (
            ticket.getStatus() == TicketStatus.SEAT_ALLOCATED_PARTIAL
            || ticket.getStatus() == TicketStatus.SEAT_ALLOCATED_ALL
        ) {
            for (var seat: ticket.getWantingSeats().values()) {
                // 현재 나한테 배정된 좌석이 있으면
                if (seat.getCurrentWaitingNumber() == ticket.getWaitingNumber()) {
                    this.allocateSeatToNextTicket(seat);
                }
            }
        }

        ticketIssueRepository.saveDomain(ticket);
        return ticket;
    }

    private void allocateSeatToNextTicket(Seat seat) {
        var nextTicket = wantingSeatsRepository.getNextTicketWantingTheSeatOrNull(
                seat.getCurrentWaitingNumber() + 1,
                seat.getPerformance().getId(),
                seat.getId()
        );

        if (nextTicket == null) {
            seat.setWaitingNumberTo(seat.getPerformance().getTicketWaitingLength()+1);

            seatService.saveDomain(seat);
            return;
        };

        nextTicket.allocateSeat(seat);

        ticketIssueRepository.saveDomain(nextTicket);
        seatService.saveDomain(seat);
    }
}
