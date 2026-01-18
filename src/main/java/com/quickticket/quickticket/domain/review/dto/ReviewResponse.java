public class ReviewResponse {
    @Builder
    public record EventPageReviewListItem(
        Long id,
        String username,
        Short userRating,
        LocalDateTIme createdAt,
        String content
    ) {}
}