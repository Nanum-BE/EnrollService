package com.nanum.enrollservice.housetour.vo;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourRequest {

    @NotNull(message = "tourDate cannot be null")
    @Schema(description = "투어 희망 날짜", defaultValue = "2022-10-12T20:00:00")
    private LocalDate tourDate;

    @Schema(description = "투어 희망 시간 ID", defaultValue = "1")
    private Long timeId;

    @Schema(description = "문의내용", defaultValue = "저녁 시간대에도 투어 가능할까요?")
    private String inquiry;

    public HouseTourDto toHouseTourDto(Long houseId, Long roomId) {
        return HouseTourDto.builder()
                .houseId(houseId)
                .roomId(roomId)
                .timeId(timeId)
                .tourDate(tourDate)
                .inquiry(inquiry)
                .houseTourStatus(HouseTourStatus.WAITING)
                .build();
    }
}
