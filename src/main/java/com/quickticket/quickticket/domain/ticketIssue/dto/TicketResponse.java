package com.quickticket.quickticket.domain.ticketIssue.dto;

import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TicketResponse {
    @Builder
    public record Details(
        Long id,

        TicketEventInfo event,

        TicketPerormanceInfo performance,

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
        public record TicketPerormanceInfo(
            Integer nth,

            LocalDateTime ticketingStartsAt,
            
            LocalDateTime ticketingEndsAt,
            
            LocalDateTime performanceStartsAt,
            
            LocalTime runningTime
        ) {}
    }
}
