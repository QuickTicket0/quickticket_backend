package com.quickticket.quickticket.domain.category.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/// CommonDto의 경우는 Request, Response 등 다양한 Dto들에게서 참조되는 Dto입니다.
/// Category라는 객체의 구조는 id와 name으로 정형화가 되어있고 향후 크게 바뀔 일이 없다고 보았습니다.
/// CommonDto를 여러 다른 Dto들이 참조를 하면, 각 Dto들에서 일일히 구현할 필요 없이 그대로 형태를 따라가서
/// JSON 속성의 형태로 정할수가 있기 때문에 코드 단축에 유리합니다.
///
/// 또한 CommonDto와 Response를 염연히 분리해서 생성했는데,
/// CommonDto는 코드 전반에 쓰이기 때문에 어떤 사용처에서 '이 카테고리 형식에 추가할 내용이 필요하다' 하면
/// 그때 비로소 CommonDto를 각 용도에 맞게 각각 구현하도록 하면 됩니다.
/// 그리고 그런 과정에서 Category 자체의 어떤 Response 형식이 바뀔 위험이 사라집니다.

@Builder
public record CategoryCommonDto(
    @NotNull
    @Min(0)
    Long id,

    @NotBlank
    String name
) {}