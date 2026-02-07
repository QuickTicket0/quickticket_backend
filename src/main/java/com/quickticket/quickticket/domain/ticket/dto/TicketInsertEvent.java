public record TicketInsertEvent(
    TicketIssueEntity entity,
    LocalDateTime createdAt
) {
    public TicketInsertEvent(TicketIssueEntity entity) {
        this.entity = entity;
        this.createdAt = LocalDateTime.now();
    }
}
