public record EventCache implements Serializable(
    String name,
    AgeRating ageRating,
    Long locationId
) {}
