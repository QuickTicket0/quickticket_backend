package com.quickticket.quickticket.config;

import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaPagingItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class TicketAllocationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;

    @Bean
    public Job ticketallocationJob() {
        return jobBuilderFactory.get("memberPointJob")
                .start(memberPointStep())
                .build();
    }

    @Bean
    public Step ticketAllocationStep() {
        return stepBuilderFactory.get("ticketAllocationStep")
                .<TicketIssueEntity, TicketIssueEntity>chunk(1000)
                .reader(memberReader())
                .processor(memberProcessor())
                .writer()
                .build();
    }

    @Bean
    public JpaPagingItemReader<TicketIssueEntity> ticketReader() {
        return new JpaPagingItemReaderBuilder<TicketIssueEntity>()
                .name("ticketReader")
                .entityManagerFactory(emf)
                .methodName("getTicketsByPerformanceId")
                .aruments(Arrays.asList("PerformanceId"))
                .sorts(Collections.singletonMap("waitingNumber", Sort.Direction.ASC))
                .queryString("SELECT m FROM Member m WHERE m.active = true")
                .pageSize(1000)
                .build();
    }
    @Bean
    public ItemProcessor<TicketIssueEntity, TicketIssueEntity> ticketAllocationProcessor() {
        return member -> {var nextWaitingNth = seat.getCurrentWaitingNumber() + 1;
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

            member.addPoints(10);
            return member;
        };
    }
}
