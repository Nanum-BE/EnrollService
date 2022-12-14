package com.nanum.enrollservice.housetour.vo;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourResponse {
    @Schema(description = "투어 신청 식별자", defaultValue = "1")
    private Long id;

    @Schema(description = "하우스 식별자", defaultValue = "1")
    private Long houseId;

    private String houseName;

    @Schema(description = "방 식별자", defaultValue = "1")
    private Long roomId;

    private String roomName;

    @Schema(description = "투어 신청 사용자 식별자", defaultValue = "1")
    private Long userId;

    @Schema(description = "투어 희망 날짜", defaultValue = "2022-10-08")
    private LocalDate tourDate;

    @Schema(description = "투어 희망 시간", defaultValue = "1")
    private String time;

    @Schema(description = "문의 내용", defaultValue = "저녁 시간대에도 투어 가능할까요?")
    private String inquiry;

    @Schema(description = "신청 상태", defaultValue = "WAITING")
    private HouseTourStatus houseTourStatus;

    @Schema(description = "생성 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime createAt;

    @Schema(description = "수정 날짜", defaultValue = "2022-10-07T17:06:13")
    private LocalDateTime updateAt;

    public static HouseTourResponse of(HouseTour houseTour) {
        HouseTourResponse houseTourResponse = new HouseTourResponse();
        houseTourResponse.id = houseTour.getId();
        houseTourResponse.houseId = houseTour.getHouseId();
        houseTourResponse.houseTourStatus = houseTour.getHouseTourStatus();
        houseTourResponse.createAt = houseTour.getCreateAt();
        houseTourResponse.houseName = houseTour.getHouseName();
        houseTourResponse.roomId = houseTour.getRoomId();
        houseTourResponse.roomName = houseTour.getRoomName();
        houseTourResponse.userId = houseTour.getUserId();
        houseTourResponse.tourDate = houseTour.getTourDate();
        houseTourResponse.inquiry = houseTour.getInquiry();
        houseTourResponse.time = houseTour.getHouseTourTime().getTime().toString().substring(0,5);
        houseTourResponse.updateAt = houseTour.getUpdateAt();

        return houseTourResponse;
    }
}
