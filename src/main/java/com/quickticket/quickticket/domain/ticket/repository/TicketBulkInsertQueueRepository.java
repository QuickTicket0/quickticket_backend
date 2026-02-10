package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketBulkInsertQueueRepository
        extends CrudRepository<TicketBulkInsertQueueEntity, Long>,
                TicketBulkInsertQueueRepositoryCustom {
    List<TicketBulkInsertQueueEntity> getAllByUserId(Long userId);
}
