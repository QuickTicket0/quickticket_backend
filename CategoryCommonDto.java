@Builder
public record CategoryCommonDto(
    @NotNull
    @Min(0)
    Long id,

    @NotBlank
    String name
) {}