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
    /// 공연이 보여지는 이름 (엔드 앤드 등)
    private String name;
    /// 상세정보란에 보여지는 모든 텍스트
    private String description;
    /// 주 카테고리. null이 아님
    private Category category1;
    /// 부 카테고리. null 가능함
    private Category category2;
    /// 관람 제한 등급
    private AgeRating ageRating;
    private Blob thumbnailImage;
    /// 제작사 이름
    private String agentName;
    /// 주최/기획사 이름
    private String hostName;
    /// 기획사 문의 연락처. 전화번호거나 이메일 등 전부 가능
    private String contactData;
    private Location location;
}