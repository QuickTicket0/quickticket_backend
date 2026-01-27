package com.quickticket.quickticket.domain.ticket.repository;

import com.quickticket.quickticket.domain.ticket.entity.WantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.WantingSeatsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WantingSeatsRepository
        extends JpaRepository<WantingSeatsEntity, WantingSeatsId>, WantingSeatsRepositoryCustom {
}
