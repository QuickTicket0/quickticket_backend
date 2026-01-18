@Builder
public record PaymentMethodCommonDto(
    @NotNull
    PaymentMethodType methodType,

    @NotNull
    PaymentMethodDetailsCommonDto methodDetails
) {}