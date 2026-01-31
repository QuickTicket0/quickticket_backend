public record SeatCache implements Serializable(
    Long seatId,
    Long performanceId,
    Long eventId,
    Long seatClassId,
    Long seatAreaId,
    String name,
    SeatStatus status,
    Long currentWaitingNumber
) {}
