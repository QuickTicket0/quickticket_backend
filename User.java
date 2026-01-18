@Builder
@Getter
public class User {
    private Long id;
    private String username;
    private String passwordHash;
    private String realname;
    private LocalDate birthday;
    private String email;
    private String phone;
    private Long credit;
    private UserRole role;
    private LocalDateTime createdAt;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw IllegalStateException();

        this.id = id;
    }
}