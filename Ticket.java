@Builder
@Getter
public class Ticket {
    private Long id;
    private Perormance performance;
    private User user;
    private PaymentMethod paymentMethod;
    private TicketStatus status;
    private Long waitingNumber;
    private Integer personNumber;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }
}