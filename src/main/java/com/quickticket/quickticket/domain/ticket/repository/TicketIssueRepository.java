package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketIssueRepository extends JpaRepository<TicketIssueEntity, Long> {
    TicketIssueEntity getById(Long id);
}
