package com.nanum.enrollservice.movein.dto;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MoveInUpdateDto {
    private Long moveInId;
    private MoveInStatus moveInStatus;
    private LocalDate expireDate;

    public MoveIn toEntity(MoveIn moveIn, LocalDate expireDate) {
        return MoveIn.builder()
                .id(moveInId)
                .houseId(moveIn.getHouseId())
                .houseName(moveIn.getHouseName())
                .hostId(moveIn.getHostId())
                .roomId(moveIn.getRoomId())
                .roomName(moveIn.getRoomName())
                .userId(moveIn.getUserId())
                .expireDate(expireDate)
                .detailAddress(moveIn.getDetailAddress())
                .lotAddress(moveIn.getLotAddress())
                .zipCode(moveIn.getZipCode())
                .streetAddress(moveIn.getStreetAddress())
                .houseImg(moveIn.getHouseImg())
                .moveDate(moveIn.getMoveDate())
                .inquiry(moveIn.getInquiry())
                .moveInStatus(moveInStatus)
                .build();
    }
}
