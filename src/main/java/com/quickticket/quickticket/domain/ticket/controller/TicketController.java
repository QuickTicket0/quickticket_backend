package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.ticket.dto.TicketRequest;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import com.quickticket.quickticket.domain.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TicketResponse.Preset> presetTicket(
            @ModelAttribute TicketRequest.Preset dto,
            @AuthenticationPrincipal UserDetails user
    ) {
        TicketResponse.Preset res = service.presetTicket(dto, user.id);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/api/ticket/create")
    @ResponseBody
    public ResponseEntity<TicketResponse.Ticket> createNewTicket(
            @ModelAttribute TicketRequest.Ticket dto,
            @AuthenticationPrincipal UserDetails user
    ) {
        // service가 TicketResponse.Ticket을 반환한다고 가정
        TicketResponse.Ticket res = service.createNewTicket(dto, user.id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/ticketSuccess/cancel")
    @ResponseBody
    public ResponseEntity<TicketResponse.Cancel> cancelTicket(
            @ModelAttribute TicketRequest.Cancel dto,
            @AuthenticationPrincipal UserDetail user
    ){
        TicketResponse.Cancel res = service.cancelTicket(dto, user.id);
        return ResponseEntity.ok(res);
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
        return "registerTicket"; }
}
