package com.nanum.enrollservice.movein.vo;

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

    private String streetAddress;

    private String lotAddress;

    private String detailAddress;

    private String zipCode;

    private Long roomId;

    private String roomName;

    List<Long> userIds;

    private LocalDate moveDate;

    private LocalDate contractEndDate;
}
