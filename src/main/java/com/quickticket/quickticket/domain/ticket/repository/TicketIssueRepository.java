package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.entity.TicketIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketIssueRepository
        extends JpaRepository<TicketIssueEntity, Long>,
                TicketIssueRepositoryCustom,
                TicketIssueBulkInsertRepository {
    List<TicketIssueEntity> getAllByUser_Id(Long userId);
}
