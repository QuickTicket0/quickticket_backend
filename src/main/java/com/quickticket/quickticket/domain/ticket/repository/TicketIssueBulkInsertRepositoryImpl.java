package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketInsertEvent;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import com.quickticket.quickticket.domain.ticket.mapper.TicketIssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@RequiredArgsConstructor
public class TicketIssueBulkInsertRepositoryImpl implements TicketIssueBulkInsertRepository {
    private static final int EVENT_BUFFER_SIZE = 100;
    
    private final RedisAtomicLong ticketIssueIdGenerator;
    private final RedisTemplate redisTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final TicketIssueMapper ticketIssueMapper;
    private final @Lazy TicketIssueRepository ticketIssueRepository;
    private final ArrayList<TicketInsertEvent> eventBuffer = new ArrayList<>();

    public Ticket saveDomainForBulk(Ticket domain) {
        if (domain.getId() != null) {
            ticketIssueRepository.saveDomain(domain);
            return domain;
        }

        domain = domain.withId(ticketIssueIdGenerator.incrementAndGet());

        var entity = ticketIssueMapper.toEntity(domain);
        redisTemplate.opsForValue().set("sync:ticket-issue-cache:" + entity.getTicketIssueId(), entity);
        
        this.addToBuffer(TicketInsertEvent.from(domain));
        
        return domain;
    }

    private void addToBuffer(TicketInsertEvent event) {
        this.eventBuffer.add(event);
        
        if (this.eventBuffer.size() < EVENT_BUFFER_SIZE) {
            return;
        }

        rabbitTemplate.convertAndSend("ticket.exchange", "ticket.ticket-issue.insert", eventBuffer);
    }
}
