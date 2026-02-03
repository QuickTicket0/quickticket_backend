package com.quickticket.quickticket.config;

import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.seat.domain.Seat;
import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class TicketAllocationBatchConfig extends DefaultBatchConfiguration {
    private final EntityManagerFactory emf;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job ticketAllocationJob() {
        return new JobBuilder("memberPointJob", jobRepository)
                .start(ticketAllocationStep())
                .build();
    }

    @Bean
    public Step ticketAllocationStep() {
        return new StepBuilder("ticketAllocationStep", jobRepository)
                .<TicketIssueEntity, TicketIssueEntity>chunk(100)
                .reader(ticketReader())
                .processor(ticketAllocationProcessor())
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public JpaPagingItemReader<TicketIssueEntity> ticketReader() {
        return new JpaPagingItemReaderBuilder<TicketIssueEntity>()
                .name("ticketReader")
                .entityManagerFactory(emf)
                .queryString("SELECT m FROM Member m WHERE m.active = true")
                .pageSize(100)
                .build();
    }
    @Bean
    public ItemProcessor<TicketIssueEntity, TicketIssueEntity> ticketAllocationProcessor() {
        return ticket -> {
            var nextWaitingNth = seat.getCurrentWaitingNumber() + 1;

            var waitingLength = seat.getPerformance().getTicketWaitingLength();
            var performanceId = seat.getPerformance().getId();

            // 더 이상 대기자가 없으면 종료
            if (nextWaitingNth > waitingLength) {
                return;
            }

            // 다음 순번이 이 좌석을 원하는지 확인
            if (wantingSeatsRepository.doesWaitingNthWantsTheSeat(
                    nextWaitingNth,
                    performanceId,
                    seat.getId()
            )) {
                var currentWaitingTicket =
                        ticketIssueRepository.getDomainByWaitingNumber(
                                nextWaitingNth,
                                performanceId
                        );

                this._allocateSeatToTicket(seat, currentWaitingTicket);
            }

            return ticket;
        };
    }

    private void _allocateSeatToTicket(Seat seat, Ticket ticket) {
        ticket.allocateSeat(seat);

        var seatPayment = SeatPaymentIssue.createAndPay()
                .seat(seat)
                .user(ticket.getUser())
                .ticketIssue(ticket)
                .amount(seat.getSeatClass().getPrice())
                .build();

        seatPaymentService.saveDomain(seatPayment);
        ticketIssueRepository.saveDomain(ticket);
    }
}
