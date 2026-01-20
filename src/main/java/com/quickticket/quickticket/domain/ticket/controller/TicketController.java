package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.dto.SeatPaymentResponse;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {
    @GetMapping("/ticketSuccess")
    public String ticketSuccess(Model model) {

        // 모델에 각각 정보를 넘긴다고 가정함(콘서트정보, 티켓정보, 각좌석결제정보)
        //  model.addAttribute("event", eventDto);
        //  model.addAttribute("ticket", ticketDto);
        //  model.addAttribute("payment", SeatPaymentDto);
        return "ticketSuccess";
    }

    @GetMapping("/cancelTicketSuccess")
    public String cancelTicketSuccess(Model model) {

        // 모델에 각각 정보를 넘긴다고 가정함(콘서트정보, 티켓정보, 결제정보)
        //  model.addAttribute("event", eventDto);
        //  model.addAttribute("ticket", ticketDto);
        //  model.addAttribute("payment", paymentDto);
        return "cancelTicketSuccess";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model) {

        //ticketList를 리스트 형태로 넘겼다고 가정함
        return "myPage/myTickets";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable String ticketId) {

        // 모델에 각각 정보를 넘긴다고 가정함(콘서트정보, 티켓정보, 결제정보, 좌석별가격정보-리스트형태)
        //  model.addAttribute("event", eventDto);
        //  model.addAttribute("ticket", ticketDto);
        //  model.addAttribute("payment", paymentDto);
        //  model.addAttribute("seatInfoList", seatInfoList);
        return "myPage/myTicket";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) { return "registerTicket"; }
}
