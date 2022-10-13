package com.nanum.enrollservice.housetour.vo;

import com.nanum.common.HouseTourStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class HouseTourResponse {
    @Schema(description = "투어 신청 식별자", defaultValue = "1")
    private Long id;

    @Schema(description = "하우스 식별자", defaultValue = "1")
    private Long houseId;

    @Schema(description = "방 식별자", defaultValue = "1")
    private Long roomId;

    @Schema(description = "사용자 식별자", defaultValue = "1")
    private Long userId;

    @Schema(description = "투어 희망 날짜", defaultValue = "2022-10-08T17:06:13")
    private LocalDateTime tourDate;

    @Schema(description = "문의 내용", defaultValue = "저녁 시간대에도 투어 가능할까요?")
    private String inquiry;

    @Schema(description = "신청 상태", defaultValue = "WAITING")
    private HouseTourStatus houseTourStatus;

    @Schema(description = "생성 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime createAt;

    @Schema(description = "수정 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime updateAt;
}
