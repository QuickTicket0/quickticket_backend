public class EventResponse {
    @Builder
    public record ListItem(
        String name,
        
        String description,

        String category1Name,

        String category2Name,

        AgeRating ageRating,

        Blob thumbnailImage
    ) {}

    @Builder
    public record Details(
        Long id,

        String name,

        String description,

        CategoryCommonDto category1,

        CategoryCommonDto category2,

        AgeRating ageRating,

        Blob thumbnailImage,

        String agentName,

        String hostName,

        String contactData,

        LocationCommonDto location
    ) {}
}
