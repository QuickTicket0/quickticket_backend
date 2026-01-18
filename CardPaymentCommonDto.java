@Builder
public record CardPaymentCommonDto(
    @NotBlank
    String cardNumber,

    @NotBlank
    String cvs,

    @NotBlank
    LocalDate expirationPeroid,

    @NotNull
    Boolean isActive,

    @NotBlank
    String bankName
) implements PaymentMethodDetailsCommonDto {}