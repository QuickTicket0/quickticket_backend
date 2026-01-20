package com.quickticket.quickticket.domain.payment.method.domain;

import com.quickticket.quickticket.domain.user.domain.User;

/// 결제 수단은 여러개이고 각 수단마다 필요한 정보가 다르기 때문에,
/// 모든 결제수단에서 공통적으로 쓰는 데이터만 추상 클래스가 갖고 나머지는 각 수단이 상속하여 구현합니다
public abstract class PaymentMethod {
    private Long id;
    private User user;

    /// 각 수단이 자기와 맞는 타입의 Enum 값을 반환해야 합니다.
    public abstract PaymentMethodType getType();

    /// 반드시 create로 생성된 객체가 DB에 할당되었을 상황에만 호출하세요
    public void assignId(Long id) {
        if (this.id != null) throw new IllegalStateException();

        this.id = id;
    }
}