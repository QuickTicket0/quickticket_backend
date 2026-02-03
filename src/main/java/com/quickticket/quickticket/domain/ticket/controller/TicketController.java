package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
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

    private final TicketService service;
    private final UserService userService;

    private Long getUserId(UserDetails user) {
        return userService.getDomainByUsername(user.getUsername()).getId();
    }

    @PostMapping("/api/ticket/preset")
    @ResponseBody
    public void presetTicket(
            @ModelAttribute TicketRequest.Preset dto,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = getUserId(user);
        service.presetTicket(dto, userId);
    }

    @PostMapping("/api/ticket/create")
    @ResponseBody
    public void createNewTicket(
            @ModelAttribute TicketRequest.Ticket dto,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = getUserId(user);
        service.createNewTicket(dto, userId);
    }

    @PostMapping("/ticketSuccess/cancel")
    @ResponseBody
    public void cancelTicket(
            @ModelAttribute TicketRequest.Cancel dto,
            @AuthenticationPrincipal UserDetails user
    ) {
        Long userId = getUserId(user);
        service.cancelTicket(dto, userId);
    }

    @GetMapping("/cancelTicketSuccess/{ticketId}")
    public String cancelTicketSuccess(Model model, @PathVariable Long ticketId) {
        TicketResponse.Details details = service.getResponseDetailsById(ticketId);
        model.addAttribute("ticket", details);
        return "cancelTicketSuccess";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable Long ticketId) {
        TicketResponse.Details details = service.getResponseDetailsById(ticketId);
        model.addAttribute("ticket", details);
        return "myPage/myTicket";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model, @AuthenticationPrincipal UserDetails user) {
        Long userId = getUserId(user);

        var list = service.getMyTickets(userId);

        model.addAttribute("tickets", list);
        return "myPage/myTickets";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) {
        return "registerTicket";
    }
}
