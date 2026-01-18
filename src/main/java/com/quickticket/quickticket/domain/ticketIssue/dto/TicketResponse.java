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

            LocalDateTIme ticketingStartsAt,
            
            LocalDateTIme ticketingEndsAt,
            
            LocalDateTIme performanceStartsAt,
            
            LocalTime runningTime
        ) {}
    }
}
