package com.nanum.enrollservice.movein.vo;

import com.nanum.common.MoveInStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class MoveInRequest {

    @NotNull(message = "userId cannot be null")
    @Schema(description = "사용자 식별자", defaultValue = "1")
    private Long userId;

    @NotNull(message = "moveDate cannot be null")
    @Schema(description = "희망 입주일", defaultValue = "2022-10-12T20:00:00")
    private LocalDateTime moveDate;

    @Schema(description = "문의 내용", defaultValue = "문의 내용 테스트")
    private String questionContent;
}
