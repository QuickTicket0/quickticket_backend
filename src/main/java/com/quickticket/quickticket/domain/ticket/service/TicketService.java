package com.quickticket.quickticket.domain.ticket.service;

import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.method.service.PaymentMethodService;
import com.quickticket.quickticket.domain.payment.seatPayment.service.SeatPaymentService;
import com.quickticket.quickticket.domain.performance.service.PerformanceService;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.seat.domain.SeatClass;
import com.quickticket.quickticket.domain.seat.domain.SeatStatus;
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
    private final PerformanceService performanceService;
    private final UserService userService;
    private final PaymentMethodService paymentMethodService;

    private final TicketIssueRepository ticketIssueRepository;
    private final WantingSeatsRepository wantingSeatsRepository;

    public List<TicketResponse.ListItem> getMyTickets(Long userId) {
        return ticketIssueRepository.getListItemsByUserId(userId);
    }

    public TicketResponse.Details getResponseDetailsById(Long ticketId) {
        return ticketIssueRepository.getDetailsById(ticketId);
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
    @DistributedLock(key = "lock:ticket-allocation-for-performance:#dto.performanceId()")
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

        newTicket.ticketToPerformance(performance);

        // 비어있는 좌석 있을시 바로 배정
        for (var seat: wantingSeats.values()) {
            if (seat.getStatus() == SeatStatus.AVAILABLE) {
                seat.setWaitingNumberForTicket(newTicket);
                seatService.saveDomain(seat);
            }
        }

        newTicket = ticketIssueRepository.saveDomainForBulk(newTicket);
        performance = performanceService.saveDomain(performance);
        return newTicket;
    }

    @Transactional
    @DistributedLock(key = "lock:ticket-allocation-for-performance:#dto.performanceId()")
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
            seat.setStatusToAvailable();
            seatService.saveDomain(seat);
            return;
        };

        nextTicket.allocateSeat(seat);

        ticketIssueRepository.saveDomain(nextTicket);
        seatService.saveDomain(seat);
    }
}
