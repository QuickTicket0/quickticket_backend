public record PerformanceCache implements Serializable(
    Integer nth,
    String ticketingStartsAt,
    String ticketingEndsAt,
    String performanceStartsAt,
    String runningTime,
    List<String> performarsName
) {}
