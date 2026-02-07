package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TicketIssueRepository
        extends JpaRepository<TicketIssueEntity, Long>, TicketIssueRepositoryCustom, TicketIssueBulkinsertRepository {
    List<TicketIssueEntity> getAllByUser_Id(Long userId);
}
