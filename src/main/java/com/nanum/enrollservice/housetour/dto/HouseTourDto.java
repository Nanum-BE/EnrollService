package com.nanum.enrollservice.housetour.dto;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class HouseTourDto {
    private Long userId;
    private Long houseId;
    private Long roomId;
    private LocalDateTime tourDate;
    private String inquiry;
    private HouseTourStatus houseTourStatus;

    public HouseTour dtoToEntity() {
        return HouseTour.builder()
                .roomId(roomId)
                .userId(userId)
                .tourDate(tourDate)
                .inquiry(inquiry)
                .houseTourStatus(houseTourStatus)
                .build();
    }
}
