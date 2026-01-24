package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService service;

    @PostMapping("/api/ticket/preset")
    public void presetTicket(@ModelAttribute TicketRequest.Preset dto, UserDetails user) {
        service.presetTicket(dto, user.id);
    }

    @PostMapping("/api/ticket/create")
    public void createNewTicket(@ModelAttribute TicketRequest.Ticket dto, UserDetails user) {
        service.createNewTicket(dto, user.id);
    }

    @GetMapping("/ticketSuccess/{ticketId}")
    public String ticketSuccess(Model model, @PathVariable Long ticketId) {
        var details = service.getResponseDetailsById(ticketId);

        model.addAttribute(details);

        return "ticketSuccess";
    }

    @GetMapping("/cancelTicketSuccess/{ticketId}")
    public String cancelTicketSuccess(Model model, @PathVariable Long ticketId) {
        var details = service.getResponseDetailsById(ticketId);

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
        var details = service.getResponseDetailsById(ticketId);

        model.addAttribute(details);

        return "myPage/myTicket";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) { return "registerTicket"; }
}
