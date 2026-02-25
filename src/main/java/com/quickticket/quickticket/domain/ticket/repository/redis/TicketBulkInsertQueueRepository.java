package com.quickticket.quickticket.domain.ticket.repository.redis;

import com.quickticket.quickticket.domain.ticket.entity.redis.TicketBulkInsertQueueEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketBulkInsertQueueRepository
        extends CrudRepository<TicketBulkInsertQueueEntity, Long>,
                TicketBulkInsertQueueRepositoryCustom {
    List<TicketBulkInsertQueueEntity> getAllByUserId(Long userId);
}
