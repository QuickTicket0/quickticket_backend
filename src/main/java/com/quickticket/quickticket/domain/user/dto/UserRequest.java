public class UserRequest {
    @Builder
    public record Login(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 50)
        String password,
    ) {}

    @Builder
    public record Signup(
        @NotBlank
        @Size(max = 50)
        String username,

        @NotBlank
        @Size(max = 50)
        String password,

        @NotBlank
        @Size(max = 20)
        String realname,

        @NotNull
        LocalDate birthday,

        @Email
        @Size(max = 30)
        String email,

        @NumberString
        @Size(max = 30)
        String phone
    ) {}

    @Builder
    public record EditInfo(
        @NotNull
        @Min(0)
        Long id,

        @Size(max = 50)
        String username,

        @Size(max = 50)
        String password,

        @Size(max = 20)
        String realname,
        
        LocalDate birthday,
        
        @Email
        @Size(max = 30)
        String email,

        @Phone
        @Size(max = 30)
        String phone
    ) {}
    
    @Builder
    public record Delete(
        @NotNull
        @Min(0)
        Long id,

        @NotBlank
        @Size(max = 50)
        String password
    ) {}
}