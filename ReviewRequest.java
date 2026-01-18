public class ReviewRequest {
    @Builder
    public record Create(
        @NotNull
        @Min(0)
        Long eventId,

        @NotNull
        @Min(0)
        Short userRating,

        @NotBlank
        @Size(max = 1000)
        String content,
    ) {}
}