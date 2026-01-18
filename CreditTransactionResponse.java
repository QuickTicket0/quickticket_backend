public class CreditTransactionResponse {
    @Builder
    public record Details(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {}
}