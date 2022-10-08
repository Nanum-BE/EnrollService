package com.nanum.enrollservice.housetour.vo;

import com.nanum.common.HouseTourStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class HouseTourUpdateRequest {

    @NotNull(message = "houseTourId cannot be null")
    @Schema(description = "하우스 투어 식별자", defaultValue = "1")
    private Long houseTourId;

    @NotNull(message = "houseTourStatus cannot be null")
    @Schema(description = "하우스 신청 승인 여부", defaultValue = "APPROVED")
    private HouseTourStatus houseTourStatus;
}
