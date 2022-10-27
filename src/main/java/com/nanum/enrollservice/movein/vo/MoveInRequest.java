package com.nanum.enrollservice.movein.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MoveInRequest {

    @NotNull(message = "moveDate cannot be null")
    @Schema(description = "희망 입주일", defaultValue = "2022-10-12T20:00:00")
    private LocalDate moveDate;

    @Schema(description = "문의 내용", defaultValue = "문의 내용 테스트")
    private String inquiry;
}
