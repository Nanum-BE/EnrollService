package com.nanum.enrollservice.movein.dto;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class MoveInDto {
    private Long houseId;
    private Long roomId;
    private LocalDate moveDate;
    private Long hostId;
    private String inquiry;
    private MoveInStatus moveInStatus;

    public MoveIn toEntity(Long userId, Long hostId) {
        return MoveIn.builder()
                .houseId(houseId)
                .roomId(roomId)
                .userId(userId)
                .hostId(hostId)
                .moveDate(moveDate)
                .inquiry(inquiry)
                .moveInStatus(moveInStatus)
                .build();
    }
}
