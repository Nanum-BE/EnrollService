package com.nanum.common;

public enum HouseTourStatus {

    WAITING("대기 중"),
    APPROVED("승인 완료됨"),
    REJECTED("거부됨"),
    CANCELED("취소됨"),
    TOUR_COMPLETED("투어 완료됨");

    private String houseTourStatus;

    HouseTourStatus(String houseTourStatus) {
        this.houseTourStatus = houseTourStatus;
    }

    public String getHouseTourStatus() {
        return houseTourStatus;
    }
}
