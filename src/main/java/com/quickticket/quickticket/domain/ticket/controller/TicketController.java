package com.quickticket.quickticket.domain.ticket.controller;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.dto.EventResponse;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.method.domain.PaymentMethodType;
import com.quickticket.quickticket.domain.payment.method.dto.CardPaymentCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssueStatus;
import com.quickticket.quickticket.domain.payment.seatPayment.dto.SeatPaymentResponse;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import com.quickticket.quickticket.domain.ticket.dto.TicketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TicketController {
    @GetMapping("/ticketSuccess")
    public String ticketSuccess(Model model) {

        // model.addAttribute("ticket", ticketResponse);
        return "ticketSuccess";
    }

    @GetMapping("/cancelTicketSuccess")
    public String cancelTicketSuccess(Model model) {

        //model.addAttribute("ticket", ticketResponse);
        return "cancelTicketSuccess";
    }

    @GetMapping("/myPage/tickets")
    public String myTickets(Model model) {
        // [테스트용 데이터]
        /*
        // [공연 정보]
        TicketResponse.Details.TicketEventInfo eventInfo = TicketResponse.Details.TicketEventInfo.builder()
                .name("2026 이창섭 단독 콘서트 〈EndAnd〉 - 수원")
                .dateRange("2026.02.09 - 2026.02.10")
                .cast("이창섭")
                .ageRating("만 7세 이상")
                .build();

        // [좌석 정보]
        Map<Long, TicketResponse.Details.TicketPerformanceInfo.TicketSeatInfo> seatsMap = new HashMap<>();

        seatsMap.put(101L, TicketResponse.Details.TicketPerformanceInfo.TicketSeatInfo.builder()
                .id(101L).name("A열 5번").seatClass("VIP석").price(150000L).build());

        seatsMap.put(102L, TicketResponse.Details.TicketPerformanceInfo.TicketSeatInfo.builder()
                .id(102L).name("C열 12번").seatClass("R석").price(130000L).build());

        // [회차 정보]
        TicketResponse.Details.TicketPerformanceInfo performanceInfo = TicketResponse.Details.TicketPerformanceInfo.builder()
                .nth(1)
                .performanceStartsAt(LocalDateTime.of(2026, 2, 9, 17, 0))
                .runningTime(LocalTime.of(2, 0))
                .seats(seatsMap)
                .build();

        // [결제 내역]
        List<TicketResponse.Details.TicketSeatPaymentInfo> paymentList = List.of(
                new TicketResponse.Details.TicketSeatPaymentInfo(
                        SeatPaymentIssueStatus.PAID, LocalDateTime.now(), 150000, 101L),
                new TicketResponse.Details.TicketSeatPaymentInfo(
                        SeatPaymentIssueStatus.PAID, LocalDateTime.now(), 130000, 102L)

        );

        // [결제 수단]
        CardPaymentCommonDto cardDetail = CardPaymentCommonDto.builder()
                .bankName("신한카드")
                .cardNumber("1234-5678-****-****")
                .cvs("123")
                .expirationPeriod(LocalDate.of(2030, 12, 31))
                .isActive(true)
                .build();

        PaymentMethodCommonDto paymentMethod = new PaymentMethodCommonDto(
                PaymentMethodType.CARD,
                cardDetail
        );

        // 모델에 넘길 DTO
        TicketResponse.Details ticketResponse = TicketResponse.Details.builder()
                .id(1234567L)
                .status(TicketStatus.WAITING)
                .personNumber(2)
                .createdAt(LocalDateTime.now())
                .canceledAt(LocalDateTime.now())
                .event(eventInfo)
                .performance(performanceInfo)
                .seatPayments(paymentList)
                .paymentMethod(paymentMethod)
                .build();
        */

        //List<TicketResponse.Details> ticketList = List.of(ticketResponse);
        //model.addAttribute("ticketList", ticketList);
        return "myPage/myTickets";
    }

    @GetMapping("/myTicket/{ticketId}")
    public String myTicket(Model model, @PathVariable String ticketId) {

       //model.addAttribute("ticket", ticketResponse);
        return "myPage/myTicket";
    }

    @GetMapping("/registerTicket")
    public String registerTicket(Model model) { return "registerTicket"; }
}

