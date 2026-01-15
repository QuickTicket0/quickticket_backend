package com.quickticket.quickticket.domain.ticketIssue.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TicketIssueController {
    @GetMapping("/ticketSuccess")
    public String ticketSuccess(Model model) {

        return "ticketSuccess";
    }

    @GetMapping("/cancelTicketSuccess")
    public String cancelTicketSuccess(Model model) {
        return "cancelTicketSuccess";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model) {
        return "myPage/myTickets";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable String ticketId) {
        return "myPage/myTicket";
    }
}
