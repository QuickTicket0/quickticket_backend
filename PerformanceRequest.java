public class PerformanceRequest {
    @Builder
    public record Create(
        @NotNull
        @Min(0)
        Long eventId,

        @NotNull
        @Min(1)
        Integer nth,

        @Min(1)
        Integer targetSeatNumber,

        List<String> performersName,

        @NotNull
        LocalDateTIme ticketingStartsAt,
        
        @NotNull
        LocalDateTIme ticketingEndsAt,
        
        @NotNull
        LocalDateTIme performanceStartsAt,
        
        @NotNull
        LocalTime runningTime
    ) {}

    @Builder
    public record Edit(
        @NotNull
        @Min(0)
        Long id,

        @Min(1)
        Integer nth,

        @Min(1)
        Integer targetSeatNumber,

        List<String> performersName,
        
        LocalDateTIme ticketingStartsAt,
        
        LocalDateTIme ticketingEndsAt,
        
        LocalDateTIme performanceStartsAt,
        
        LocalTime runningTime
    ) {}

    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}