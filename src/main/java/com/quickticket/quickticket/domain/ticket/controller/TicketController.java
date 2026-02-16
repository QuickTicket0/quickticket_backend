package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
import com.quickticket.quickticket.domain.user.domain.User;
import com.quickticket.quickticket.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/api/ticket/preset")
    @ResponseBody
    public void presetTicket(
            @ModelAttribute TicketRequest.Preset dto,
            @AuthenticationPrincipal User user
    ) {
        ticketService.presetTicket(dto, user.getId());
    }

    @PostMapping("/api/ticket/create")
    @ResponseBody
    public void createNewTicket(
            @ModelAttribute TicketRequest.Ticket dto,
            @AuthenticationPrincipal User user
    ) {
        ticketService.createNewTicket(dto, user.getId());
    }

    @PostMapping("/ticketSuccess/cancel")
    @ResponseBody
    public void cancelTicket(
            @ModelAttribute TicketRequest.Cancel dto,
            @AuthenticationPrincipal User user
    ) {
        ticketService.cancelTicket(dto, user.getId());
    }

    @GetMapping("/cancelTicketSuccess/{ticketId}")
    public String cancelTicketSuccess(
            Model model,
            @PathVariable Long ticketId
    ) {
        TicketResponse.Details details = ticketService.getResponseDetailsById(ticketId);
        model.addAttribute("ticket", details);
        return "cancelTicketSuccess";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(
            Model model,
            @PathVariable Long ticketId
    ) {
        TicketResponse.Details details = ticketService.getResponseDetailsById(ticketId);
        model.addAttribute("ticket", details);
        return "myPage/myTicket";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        var list = ticketService.getMyTickets(user.getId());

        model.addAttribute("tickets", list);
        return "myPage/myTickets";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) {
        return "registerTicket";
    }
}
