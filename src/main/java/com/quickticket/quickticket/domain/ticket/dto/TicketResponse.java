package com.quickticket.quickticket.domain.ticket.dto;

import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TicketResponse {
    /// 예매 상세정보 페이지
    @Builder
    public record Details(
        Long id,

        TicketEventInfo event,

        TicketPerformanceInfo performance,

        PaymentMethodCommonDto paymentMethod,

        TicketStatus status,

        Long waitingNumber,

        Integer personNumber
    ) {
        @Builder
        public record TicketEventInfo(
            String ageRating,

            Blob thumbnailImage,

            LocationCommonDto location
        ) {}

        @Builder
        public record TicketPerformanceInfo(
            Integer nth,

            LocalDateTime ticketingStartsAt,
            
            LocalDateTime ticketingEndsAt,
            
            LocalDateTime performanceStartsAt,
            
            LocalTime runningTime
        ) {}
    }
}
