@Builder
public record LocationCommonDto(
    @NotNull
    @Min(0)
    Long id,
    
    @NotBlank
    String name,

    String zipNumber,

    String sido,

    String siGunGu,

    String eupMyun,

    String doroCode,

    String doroName,
    
    @NumberString
    String phone
) {}