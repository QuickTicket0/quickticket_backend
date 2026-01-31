public record EventCache implements Serializable(
    String name,
    String range,
    String casting,
    AgeRating ageRating,
    Long locationId
) {}
