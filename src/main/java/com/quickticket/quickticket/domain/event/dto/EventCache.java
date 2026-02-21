package com.quickticket.quickticket.domain.event.dto;

import com.quickticket.quickticket.domain.event.domain.AgeRating;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record EventCache(
    Long id,
    String name,
    AgeRating ageRating,
    Long locationId
) implements Serializable {
    /**
     * serialVersionUID: 객체의 버전 번호입니다.
     * 나중에 코드가 수정되어도 Redis에 저장된 기존 캐시 데이터와 충돌나지 않게 보호해줌
     * 이 번호를 명시하지 않으면 자바가 자동으로 번호를 만드는데, 필드가 조금만 바뀌어도
     * 번호가 달라져서 캐시를 읽어올 때 에러(InvalidClassException)가 날 수 있음
     */
    private static final long serialVersionUID = 1L;
}
