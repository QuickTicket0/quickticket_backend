package com.quickticket.quickticket.domain.ticket.repository.jpa;

import com.quickticket.quickticket.domain.ticket.entity.jpa.WantingSeatsEntity;
import com.quickticket.quickticket.domain.ticket.entity.jpa.WantingSeatsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WantingSeatsRepository
        extends JpaRepository<WantingSeatsEntity, WantingSeatsId>, WantingSeatsRepositoryCustom {
}
