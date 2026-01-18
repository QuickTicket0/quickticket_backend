@Builder(access = PRIVATE)
@Getter
public class CreditTransaction {
    private Long id;
    private TransactionType type;
    private Long changeAmount;
    private Long balanceAfter;
    private LocalDateTime createdAt;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }

    public static CreditTransaction create(
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {
        validateBalanceAfter(balanceAfter);

        return User.builder()
            .transactionType(transactionType)
            .changeAmount(changeAmount)
            .balanceAfter(balanceAfter)
            .createdAt(createdAt)
            .build();
    }

    public static CreditTransaction recreate(
        Long id,
        TransactionType transactionType,
        Long changeAmount,
        Long balanceAfter,
        LocalDateTime createdAt
    ) {
        return User.builder()
            .id(id)
            .transactionType(transactionType)
            .changeAmount(changeAmount)
            .balanceAfter(balanceAfter)
            .createdAt(createdAt)
            .build();
    }

    private static void validateBalanceAfter(Long balanceAfter) {
        if (
            balanceAfter == null
            || balanceAfter < 0
        ) {
            throw new IllegalArgumentException();
        }
    }
}