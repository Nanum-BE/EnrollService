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
    private Long houseId;
    private Long roomId;
    private Long timeId;
    private LocalDate tourDate;
    private Long hostId;
    private String inquiry;
    private HouseTourStatus houseTourStatus;

    public HouseTour dtoToEntity(HouseTourTime houseTourTime, Long userId, Long hostId) {
        return HouseTour.builder()
                .houseId(houseId)
                .roomId(roomId)
                .userId(userId)
                .houseTourTime(houseTourTime)
                .hostId(hostId)
                .tourDate(tourDate)
                .inquiry(inquiry)
                .houseTourStatus(houseTourStatus)
                .build();
    }
}
