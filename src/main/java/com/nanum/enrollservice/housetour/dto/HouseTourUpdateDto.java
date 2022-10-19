package com.nanum.enrollservice.housetour.dto;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseTourUpdateDto {
    private Long houseTourId;
    private HouseTourStatus houseTourStatus;

    public HouseTour toEntity(HouseTour houseTour) {
        return HouseTour.builder()
                .id(houseTourId)
                .houseId(houseTour.getHouseId())
                .roomId(houseTour.getRoomId())
                .userId(houseTour.getUserId())
                .houseTourTime(HouseTourTime.builder()
                        .id(houseTour.getHouseTourTime().getId())
                        .time(houseTour.getHouseTourTime().getTime())
                        .build())
                .tourDate(houseTour.getTourDate())
                .hostId(houseTour.getHostId())
                .inquiry(houseTour.getInquiry())
                .houseTourStatus(houseTourStatus)
                .build();
    }
}
