package com.nanum.common;

public enum RoomStatus {
    WAITING("대기 중"),
    PROGRESS("진행 중"),
    COMPLETION("입주 완료"),
    REPAIR("수리 중");

    private String roomStatus;

    RoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomStatus() {
        return roomStatus;
    }
}
