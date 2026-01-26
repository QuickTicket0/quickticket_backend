package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.ticket.domain.Ticket;
import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
import com.quickticket.quickticket.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.apache.coyote.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketService service;

    @PostMapping("/api/ticket/preset")
    @ResponseBody
    public void presetTicket(
            @ModelAttribute TicketRequest.Preset dto,
            @AuthenticationPrincipal User user
    ) {
        Ticket res = service.presetTicket(dto, user.getId());
    }

    @PostMapping("/api/ticket/create")
    @ResponseBody
    public void createNewTicket(
            @ModelAttribute TicketRequest.Ticket dto,
            @AuthenticationPrincipal User user
    ) {
        Ticket res = service.createNewTicket(dto, user.getId());
    }

    @PostMapping("/ticketSuccess/cancel")
    @ResponseBody
    public void cancelTicket(
            @ModelAttribute TicketRequest.Cancel dto,
            @AuthenticationPrincipal User user
    ) {
        Ticket res = service.cancelTicket(dto, user.getId());
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
    public String registerTicket(Model model) {
        return "registerTicket";
    }
}
