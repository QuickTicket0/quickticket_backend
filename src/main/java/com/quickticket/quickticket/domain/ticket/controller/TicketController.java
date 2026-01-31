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
        // AccountController에서 이미 쓰는 흐름 그대로: username으로 유저 조회
        return userService.findUserByUsername(user.getUsername()).getId();
        // ⚠️ getId()가 아니라 getUserId()면 그에 맞게 1줄 수정
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
        model.addAttribute("ticket", details); // ✅ key 추가
        return "cancelTicketSuccess";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable Long ticketId) {
        TicketResponse.Details details = service.getResponseDetailsById(ticketId);
        model.addAttribute("ticket", details); // ✅ key 추가
        return "myPage/myTicket";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model, @AuthenticationPrincipal UserDetails user) {
        Long userId = getUserId(user);

        // ✅ 여기만 서비스에 "내 티켓 목록" 메서드가 있어야 합니다.
        // (없다면 만들어야 함)
        var list = service.getMyTickets(userId);

        model.addAttribute("tickets", list); // ✅ key 추가
        return "myPage/myTickets";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) {
        return "registerTicket";
    }
}
