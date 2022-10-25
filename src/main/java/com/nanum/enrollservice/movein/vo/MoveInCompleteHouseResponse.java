package com.nanum.enrollservice.movein.vo;

import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoveInCompleteHouseResponse {
    private Long houseId;

    private String houseName;

    private String houseImg;

    private Long hostId;

    private String streetAddress;

    private String lotAddress;

    private String detailAddress;

    private String zipCode;

    private Long roomId;

    private String roomName;

    List<Long> userIds;

    private LocalDate moveDate;

    private LocalDate contractEndDate;

    public MoveInCompleteHouseResponse(MoveIn moveIn, List<Long> userIds) {
        this.houseId = moveIn.getHouseId();
        this.houseName = moveIn.getHouseName();
        this.hostId = moveIn.getHostId();
        this.houseImg = moveIn.getHouseImg();
        this.streetAddress = moveIn.getStreetAddress();
        this.lotAddress = moveIn.getLotAddress();
        this.detailAddress = moveIn.getDetailAddress();
        this.zipCode = moveIn.getZipCode();
        this.roomId = moveIn.getRoomId();
        this.roomName = moveIn.getRoomName();
        this.userIds = userIds;
        this.moveDate = moveIn.getMoveDate();
        this.contractEndDate = moveIn.getExpireDate();
    }
}
