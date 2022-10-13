package com.nanum.common;

public enum MoveInStatus {

    WAITING("대기 중"),
    CONTRACTING("계약 중"),
    REJECTED("거부됨"),
    CANCELED("취소됨"),
    CONTRACT_COMPLETED("계약 완료됨");

    private String moveInStatus;

    MoveInStatus(String moveInStatus) {
        this.moveInStatus = moveInStatus;
    }

    public String getMoveInStatus() {
        return moveInStatus;
    }
}
