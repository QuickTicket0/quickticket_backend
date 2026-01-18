@Builder
@Getter
public class CardPayment extends PaymentMethod {
    private String cardNumber;
    private String cvs;
    private LocalDate expirationPeroid;
    private Boolean isActive;
    private String bankName;

    public PaymentMethodType getType() {
        return PaymentMethodType.CARD;
    }
}