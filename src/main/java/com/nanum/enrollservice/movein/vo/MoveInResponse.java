package com.nanum.enrollservice.movein.vo;

import com.nanum.common.MoveInStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class MoveInResponse {
    @Schema(description = "입주 신청 식별자", defaultValue = "1")
    private Long id;

    @Schema(description = "하우스 식별자", defaultValue = "1")
    private Long houseId;

    private String houseName;

    @Schema(description = "방 식별자", defaultValue = "1")
    private Long roomId;

    @Schema(description = "사용자 식별자", defaultValue = "1")
    private Long userId;

    @Schema(description = "입주 희망 날짜", defaultValue = "2022-16-08")
    private LocalDate moveDate;

    private LocalDate expireDate;

    @Schema(description = "문의 내용", defaultValue = "문의 테스트")
    private String inquiry;

    @Schema(description = "입주 신청 상태", defaultValue = "WAITING")
    private MoveInStatus moveInStatus;

    @Schema(description = "생성 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime createAt;

    @Schema(description = "수정 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime updateAt;
}
