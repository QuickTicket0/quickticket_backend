package com.quickticket.quickticket;

import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class TicketTest {
    @Autowired private TicketService ticketService;

    @Test
    public void test() throws Exception {
        var createDto = TicketRequest.Ticket.builder()
                        .paymentMethodId(1L)
                        .performanceId(31L)
                        .personNumber(1)
                        .wantingSeatsId(List.of(1L))
                        .build();

        ticketService.createNewTicket(createDto, 2L);
    }
}
