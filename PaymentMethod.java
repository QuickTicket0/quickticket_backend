public abstract class PaymentMethod {
    private Long id;
    private User user;

    public abstract PaymentMethodType getType();

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }
}