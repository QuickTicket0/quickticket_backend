public class CategoryResponse {
    @Builder
    public record Details(
        Long id,
        String name
    ) {}
}