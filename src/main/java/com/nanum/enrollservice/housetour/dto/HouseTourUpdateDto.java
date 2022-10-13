package com.nanum.enrollservice.housetour.dto;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
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
                .tourDate(houseTour.getTourDate())
                .inquiry(houseTour.getInquiry())
                .houseTourStatus(houseTourStatus)
                .build();
    }
}
