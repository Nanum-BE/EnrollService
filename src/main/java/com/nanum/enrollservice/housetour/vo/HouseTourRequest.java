package com.nanum.enrollservice.housetour.vo;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourRequest {
    @NotNull(message = "userId cannot be null")
    @Schema(description = "사용자 식별자", defaultValue = "1")
    private Long userId;

    @NotNull(message = "tourDate cannot be null")
    @Schema(description = "투어 희망 날짜", defaultValue = "1")
    private LocalDateTime tourDate;

    @Schema(description = "문의내용", defaultValue = "저녁 시간대에도 투어 가능할까요?")
    private String inquiry;

    public HouseTourDto toHouseTourDto(Long houseId, Long roomId) {
        return HouseTourDto.builder()
                .userId(userId)
                .houseId(houseId)
                .roomId(roomId)
                .tourDate(tourDate)
                .inquiry(inquiry)
                .houseTourStatus(HouseTourStatus.WAITING)
                .build();
    }
}
