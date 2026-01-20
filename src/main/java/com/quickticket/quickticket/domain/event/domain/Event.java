package com.quickticket.quickticket.domain.event.domain;

import com.quickticket.quickticket.domain.category.domain.Category;
import com.quickticket.quickticket.domain.location.domain.Location;
import lombok.Builder;
import lombok.Getter;

import java.sql.Blob;

/// 공연, 콘서트 등을 나타냅니다.
/// 각 회차별로 변동될수 있는 정보들은 Performance 도메인에 각각 존재합니다.
/// Performance는 Event에서 시간별로 파생되는 회차 단위입니다.
@Builder
@Getter
public class Event {
    private Long id;
    private String name;
    private String description;
    private Category category1;
    private Category category2;
    private AgeRating ageRating;
    private Blob thumbnailImage;
    private String agentName;
    private String hostName;
    private String contactData;
    private Location location;

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}