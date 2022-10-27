package com.nanum.enrollservice.movein.dto;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MoveInDto {
    private Long houseId;
    private Long roomId;
    private LocalDate moveDate;
    private Long hostId;
    private String inquiry;
    private MoveInStatus moveInStatus;

    public MoveIn toEntity(Long userId, Long hostId, String houseName, String houseImg, String streetAddress,
                           String detailAddress, String lotAddress, String zipCode, String roomName) {
        return MoveIn.builder()
                .houseId(houseId)
                .roomId(roomId)
                .userId(userId)
                .houseName(houseName)
                .houseImg(houseImg)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .lotAddress(lotAddress)
                .roomName(roomName)
                .zipCode(zipCode)
                .hostId(hostId)
                .moveDate(moveDate)
                .inquiry(inquiry)
                .moveInStatus(moveInStatus)
                .build();
    }
}
