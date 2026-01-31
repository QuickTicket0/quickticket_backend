public record SeatClassCache implements Serializable(
    Long id,
    Long eventId,
    String name,
    Long price
) {}
