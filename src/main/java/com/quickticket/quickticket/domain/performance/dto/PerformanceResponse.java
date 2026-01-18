public class PerformanceResponse {
    @Builder
    public record ListItem(
        Long id,

        PerformanceEventInfo event,

        Integer nth,

        Integer targetSeatNumber,

        List<String> performersName,

        LocalDateTIme ticketingStartsAt,

        LocalDateTIme ticketingEndsAt,
        
        LocalDateTIme performanceStartsAt,

        LocalTime runningTime
    ) {
        @Builder
        public record PerformanceEventInfo(
            Long id,

            String name,

            CategoryCommonDto category1,

            CategoryCommonDto category2,

            AgeRating ageRating,

            Blob thumbnailImage,

            LocationCommonDto location
        ) {}
    }
}
