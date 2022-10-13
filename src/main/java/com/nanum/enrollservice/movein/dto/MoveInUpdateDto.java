package com.nanum.enrollservice.movein.dto;

import com.nanum.common.HouseTourStatus;
import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveInUpdateDto {
    private Long moveInId;
    private MoveInStatus moveInStatus;

    public MoveIn toEntity(MoveIn moveIn) {
        return MoveIn.builder()
                .id(moveInId)
                .houseId(moveIn.getHouseId())
                .roomId(moveIn.getRoomId())
                .userId(moveIn.getUserId())
                .moveDate(moveIn.getMoveDate())
                .inquiry(moveIn.getInquiry())
                .moveInStatus(moveInStatus)
                .build();
    }
}
