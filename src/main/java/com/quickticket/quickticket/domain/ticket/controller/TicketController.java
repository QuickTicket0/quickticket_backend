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

    /**
     * 티켓 예매 페이지로 이동하며 선택한 예매 정보를 전달
     * 이전 상세 페이지에서 사용자가 선택한 공연 회차, 날짜, 시간, 좌석 정보 등을
     * 파라미터로 받아 예매 확인 화면에 표시할 수 있도록 모델에 담습니다.
     *
     * @param eventId            콘서트 고유 ID
     * @param performanceId      선택한 회차 ID
     * @param seatId             선택한 좌석 등급 ID
     * @param selectedDate       선택한 공연 날짜 (yyyy-MM-dd)
     * @param selectedTime       선택한 회차 및 시간 정보 (1회 오후 2:00)
     * @param seatName           선택한 좌석 등급명 (VIP석, 일반석 등)
     * @param seatPrice          선택한 좌석 가격
     * @param model              화면에 데이터를 전달하기 위한 객체
     * @return                   예매 등록 페이지 뷰 이름 ("registerTicket")
     */
    @GetMapping("/registerTicket")
    public String registerTicket(
            @RequestParam Long eventId,
            @RequestParam Long performanceId,
            @RequestParam Long seatId,
            @RequestParam String selectedDate,
            @RequestParam String selectedTime,
            @RequestParam String seatName,
            @RequestParam int seatPrice,
            Model model) {

        // 화면(registerTicket.html)에서 사용할 수 있도록 모델에 데이터 바인딩
        model.addAttribute("eventId", eventId);
        model.addAttribute("performanceId", performanceId);
        model.addAttribute("seatId", seatId);
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("selectedTime", selectedTime);
        model.addAttribute("seatName", seatName);
        model.addAttribute("seatPrice", seatPrice);
        return "registerTicket";
    }
}
