public class UserResponse {
    @Builder
    public record Details(
        Long id,

        String username,

        String realname,

        LocalDate birthday,

        String email,

        String phone,

        Long credit,

        UserRole role,
        
        LocalDateTIme createdAt
    ) {}
}
