public class PaymentMethodRequest {
    private interface MethodDetails {
    }

    @Builder
    private record CardPayment(
        @NotBlank
        @Size(max = 20)
        String cardNumber,

        @NotBlank
        @Size(max = 4)
        String cvs,

        @NotNull
        LocalDate expirationPeroid,

        @NotNull
        Boolean isActive,

        @NotBlank
        @Size(max = 30)
        String bankName
    ) implements MethodDetails {}

    @Builder
    public record AddNew(
        @NotNull
        PaymentMethodType methodType,

        @NotNull
        MethodDetails methodDetails
    ) {}

    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id
    ) {}
}