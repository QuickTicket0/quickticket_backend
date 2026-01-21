package com.quickticket.quickticket.domain.ticket.dto;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import com.quickticket.quickticket.domain.event.entity.EventEntity;
import com.quickticket.quickticket.domain.location.dto.LocationCommonDto;
import com.quickticket.quickticket.domain.payment.method.dto.PaymentMethodCommonDto;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssue;
import com.quickticket.quickticket.domain.payment.seatPayment.domain.SeatPaymentIssueStatus;
import com.quickticket.quickticket.domain.payment.seatPayment.entity.SeatPaymentIssueEntity;
import com.quickticket.quickticket.domain.performance.entity.PerformanceEntity;
import com.quickticket.quickticket.domain.seat.domain.SeatStatus;
import com.quickticket.quickticket.domain.seat.entity.SeatClassEntity;
import com.quickticket.quickticket.domain.seat.entity.SeatEntity;
import com.quickticket.quickticket.domain.ticket.domain.TicketStatus;
import lombok.Builder;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class TicketResponse {
    @Builder
    public record EventInfo(
            AgeRating ageRating,
            Blob thumbnailImage,
            LocationCommonDto location
    ) {
        public static EventInfo from(EventEntity event) {
            return EventInfo.builder()
                    .ageRating(event.getAgeRating())
                    .thumbnailImage(event.getThumbnailImage())
                    .location(LocationCommonDto.from(event.getLocation()))
                    .build();
        }
    }

    @Builder
    public record PerformanceInfo(
            Integer nth,
            LocalDateTime ticketingStartsAt,
            LocalDateTime ticketingEndsAt,
            LocalDateTime performanceStartsAt,
            LocalTime runningTime
    ) {
        public static PerformanceInfo from(PerformanceEntity performance) {
            return PerformanceInfo.builder()
                    .nth(performance.getPerformanceNth())
                    .ticketingStartsAt(performance.getTicketingStartsAt())
                    .ticketingEndsAt(performance.getTicketingEndsAt())
                    .performanceStartsAt(performance.getPerformanceStartsAt())
                    .runningTime(performance.getRunningTime())
                    .build();
        }
    }

    @Builder
    public record SeatInfo(
            Long id,
            String name,
            String area,
            Long areaId,
            String seatClass,
            Long seatClassId,
            Long price,
            SeatStatus status
    ) {
        public static SeatInfo from(SeatEntity seat) {
            var seatClass = seat.getSeatClass();
            var area = seat.getArea();

            return SeatInfo.builder()
                    .id(seat.getId().getSeatId())
                    .name(seat.getName())
                    .area(area.getName())
                    .areaId(area.getId().getSeatAreaId())
                    .seatClass(seatClass.getName())
                    .seatClassId(seatClass.getId().getSeatClassId())
                    .price(seatClass.getPrice())
                    .status(seat.getStatus())
                    .build();
        }
    }

    @Builder
    public record SeatClassInfo(
            Long id,
            String name,
            Long price
    ) {
        public static SeatClassInfo from(SeatClassEntity seatClass) {
            return SeatClassInfo.builder()
                    .id(seatClass.getId().getSeatClassId())
                    .name(seatClass.getName())
                    .price(seatClass.getPrice())
                    .build();
        }
    }

    @Builder
    public record SeatPaymentInfo(
            SeatPaymentIssueStatus status,
            LocalDateTime createdAt,
            Long amount,
            /// seats의 id에 대응됩니다
            Long paidSeatId
    ) {
        public static SeatPaymentInfo from(SeatPaymentIssueEntity issue) {
            return SeatPaymentInfo.builder()
                    .status(issue.getStatus())
                    .createdAt(issue.getCreatedAt())
                    .amount(issue.getAmount())
                    .paidSeatId(issue.getSeat().getId().getSeatId())
                    .build();
        }
    }

    /// 예매 상세정보 페이지
    @Builder
    public record Details(
        Long id,

        TicketStatus status,

        PaymentMethodCommonDto paymentMethod,

        Long waitingNumber,

        Integer personNumber,

        /// '좌석별 가격'란을 표시하기 위해 좌석 등급들 정보가 들어갑니다.
        /// 모든 등급이 들어가지 않고 사용자가 예매한 좌석의 등급만 들어갑니다.
        List<SeatClassInfo> seatClasses,

        /// 모든 좌석별 구역과 등급 정보는 여기에 id로 저장됩니다
        Map<Long, SeatInfo> seats,

        /// seats의 id에 대응되는 목록입니다
        List<Long> wantingSeatsId,

        LocalDateTime createdAt,

        /// 예매가 취소된 경우에만 null이 아닌 값이 들어갑니다
        LocalDateTime canceledAt,

        EventInfo event,

        PerformanceInfo performance,

        /// 프론트에서 각 결제 정보의 결제 상태, 금액, 좌석을 종합해서 처리해야 합니다
        List<SeatPaymentInfo> seatPayments
    ) {}

    @Builder
    public record ListItem(
            Long id,
            String eventName,
            LocalDateTime performanceStartsAt,
            Integer personNumber,
            LocalDateTime createdAt
    ) {}
}
