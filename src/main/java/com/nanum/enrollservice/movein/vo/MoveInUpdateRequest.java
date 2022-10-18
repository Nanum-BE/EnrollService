package com.nanum.enrollservice.movein.vo;

import com.nanum.common.MoveInStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MoveInUpdateRequest {

    @NotNull(message = "moveInId cannot be null")
    @Schema(description = "하우스 입주 식별자", defaultValue = "1")
    private Long moveInId;

    @NotNull(message = "moveInStatus cannot be null")
    @Schema(description = "하우스 입주 신청 상태", defaultValue = "APPROVED")
    private MoveInStatus moveInStatus;
}
