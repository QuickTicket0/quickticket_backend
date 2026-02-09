package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.entity.TicketBulkInsertQueueEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBulkInsertQueueRepository extends CrudRepository<TicketBulkInsertQueueEntity, Long> {
}
