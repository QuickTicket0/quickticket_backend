package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.dto.SeatPaymentResponse;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.repository.TicketIssueRepositoryCustom;
import com.quickticket.quickticket.domain.ticket.repository.TicketIssueRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private TicketIssueRepositoryCustom ticketIssueRepository;

    @GetMapping("/ticketSuccess/{ticketId}")
    public String ticketSuccess(Model model, @PathVariable Long ticketId) {
        var details = ticketIssueRepository.getResponseDetailsById(ticketId);

        model.addAttribute(details);

        return "ticketSuccess";
    }

    @GetMapping("/cancelTicketSuccess/{ticketId}")
    public String cancelTicketSuccess(Model model, @PathVariable Long ticketId) {
        var details = ticketIssueRepository.getResponseDetailsById(ticketId);

        model.addAttribute(details);

        return "cancelTicketSuccess";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model) {
        var list = new ArrayList<TicketResponse.ListItem>();

        model.addAttribute(list);

        return "myPage/myTickets";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable Long ticketId) {
        var details = ticketIssueRepository.getResponseDetailsById(ticketId);

        model.addAttribute(details);

        return "myPage/myTicket";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) { return "registerTicket"; }
}
