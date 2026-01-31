package com.quickticket.quickticket.domain.location.domain;

import com.quickticket.quickticket.shared.annotations.Default;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/// 주로 Event가 열리는 공연장을 나타내기 위한 장소 데이터
@Builder
@Getter
@AllArgsConstructor(onConstructor_ = {@Default})
public class Location {
    private Long id;
    /// 공연장 이름
    private String name;
    private String zipNumber;
    /// 시/도
    private String sido;
    /// 시/군/구
    private String siGunGu;
    /// 읍/면
    private String eupMyun;
    /// 도로번호
    private String doroCode;
    /// 도로명
    private String doroName;
    /// 공연장측 전화번호
    private String phone;
}