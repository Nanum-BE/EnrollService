package com.nanum.enrollservice.client.vo;

import com.nanum.common.RoomStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    @Schema(description = "방 식별자")
    private Long id;

    @Schema(description = "방 면적")
    private String area;

    @Schema(description = "방 이름 또는 호수")
    private String name;

    @Schema(description = "방 수용 가능 인원")
    private int totalMember;

    @Schema(description = "보증금")
    private int deposit;

    @Schema(description = "월임대료")
    private int monthlyRent;

    @Schema(description = "관리비")
    private int maintenanceFee;

    @Schema(description = "선불공과금")
    private int prepaidUtilityBill;

    @Schema(description = "계약종료일")
    private LocalDateTime contractEndAt;

    @Schema(description = "방 입주 상태")
    private RoomStatus status;

    @Schema(description = "방 메인 이미지 경로")
    private String mainRoomImgPath;
}
