package com.nanum.enrollservice.housetour.dto;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class HouseTourDto {
    private Long userId;
    private Long houseId;
    private Long roomId;
    private Long timeId;
    private LocalDate tourDate;
    private String inquiry;
    private HouseTourStatus houseTourStatus;

    public HouseTour dtoToEntity(HouseTourTime houseTourTime) {
        return HouseTour.builder()
                .houseId(houseId)
                .roomId(roomId)
                .userId(userId)
                .houseTourTime(houseTourTime)
                .tourDate(tourDate)
                .inquiry(inquiry)
                .houseTourStatus(houseTourStatus)
                .build();
    }
}
