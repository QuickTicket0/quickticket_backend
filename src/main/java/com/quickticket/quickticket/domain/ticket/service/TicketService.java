package com.quickticket.quickticket.domain.ticket.service;

import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.method.service.PaymentMethodService;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssueStatus;
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
import com.quickticket.quickticket.domain.ticket.repository.WantingSeatsRepositoryCustom;
import com.quickticket.quickticket.domain.user.service.UserService;
import com.quickticket.quickticket.shared.aspects.DistributedLock;
import com.quickticket.quickticket.shared.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;
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

    private final JobOperator jobOperator;
    @Qualifier("ticketAllocationJob")
    private final Job ticketAllocationJob;

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
    @DistributedLock(key = "#dto.performanceId()")
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
    @DistributedLock(key = "#dto.performanceId()")
    public Ticket cancelTicket(TicketRequest.Cancel dto, Long userId)
            throws  JobInstanceAlreadyCompleteException,
                    InvalidJobParametersException,
                    JobExecutionAlreadyRunningException,
                    JobRestartException {

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

        // TODO 좌석 다음 표 배정 로직에 batch 처리 구현
        if (
            ticket.getStatus() == TicketStatus.SEAT_ALLOCATED_PARTIAL
            || ticket.getStatus() == TicketStatus.SEAT_ALLOCATED_ALL
        ) {
            for (var seat: ticket.getWantingSeats().values()) {
                if (seat.getCurrentWaitingNumber() == ticket.getWaitingNumber()) {
                    this.allocateSeatToNextTicket(seat);
                }
            }
        }

        ticket.cancel();


        ticketIssueRepository.saveDomain(ticket);
        return ticket;
    }

    private void allocateSeatToNextTicket(Seat seat)
            throws  JobInstanceAlreadyCompleteException,
                    InvalidJobParametersException,
                    JobExecutionAlreadyRunningException,
                    JobRestartException {

        var props = new JobParametersBuilder()
                .addLong("seatId", seat.getId())
                .toJobParameters();

        jobOperator.start(ticketAllocationJob, props);
    }
}
