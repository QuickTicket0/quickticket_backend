public class TicketRequest {
    @Builder
    public record Preset(
        @NotNull
        @Min(0)
        Long performanceId,

        @Min(0)
        Long paymentMethodId,

        @Min(1)
        Integer personNumber
    ) {}

    @Builder
    public record Ticket(
        @NotNull
        @Min(0)
        Long performanceId,

        @NotNull
        @Min(0)
        Long paymentMethodId,

        @NotNull
        @Min(1)
        Integer personNumber
    ) {}

    @Builder
    public record Cancel(
        @NotNull
        @Min(0)
        Long id
    ) {}
}