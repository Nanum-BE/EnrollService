package com.nanum.enrollservice.housetour.vo;

import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.movein.domain.MoveIn;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourAndMoveIn {
    private Long myHouseId;

    private String myHouseName;

    private String myRoomName;

    private String myHouseImg;

    private Long moveInHouseId;

    private String moveInHouseName;

    private String moveInRoomName;

    private String moveInHouseImg;

    private LocalDate moveInDate;

    private Long tourHouseId;

    private String tourHouseName;

    private String tourRoomName;

    private String tourHouseImg;

    private LocalDate tourDate;

    public static HouseTourAndMoveIn of(HouseTour houseTour, MoveIn moveIn, MoveIn move) {
        HouseTourAndMoveIn houseTourAndMoveIn = new HouseTourAndMoveIn();
        houseTourAndMoveIn.myHouseId = move.getHouseId();
        houseTourAndMoveIn.myHouseName = move.getHouseName();
        houseTourAndMoveIn.myRoomName = move.getRoomName();
        houseTourAndMoveIn.myHouseImg = move.getHouseImg();
        houseTourAndMoveIn.moveInHouseId = moveIn.getHouseId();
        houseTourAndMoveIn.moveInHouseName = moveIn.getHouseName();
        houseTourAndMoveIn.moveInHouseImg = moveIn.getHouseImg();
        houseTourAndMoveIn.moveInRoomName = moveIn.getRoomName();
        houseTourAndMoveIn.moveInDate = moveIn.getMoveDate();
        houseTourAndMoveIn.tourHouseId = houseTour.getHouseId();
        houseTourAndMoveIn.tourHouseName = houseTour.getHouseName();
        houseTourAndMoveIn.tourRoomName = houseTour.getRoomName();
        houseTourAndMoveIn.tourHouseImg = houseTour.getHouseImg();
        houseTourAndMoveIn.tourDate = houseTour.getTourDate();
        return houseTourAndMoveIn;
    }

    public static HouseTourAndMoveIn ofs() {
        return new HouseTourAndMoveIn();
    }

    public static HouseTourAndMoveIn MoveInNullOf(HouseTour houseTour) {
        HouseTourAndMoveIn houseTourAndMoveIn = new HouseTourAndMoveIn();
        houseTourAndMoveIn.tourHouseId = houseTour.getHouseId();
        houseTourAndMoveIn.tourHouseName = houseTour.getHouseName();
        houseTourAndMoveIn.tourRoomName = houseTour.getRoomName();
        houseTourAndMoveIn.tourHouseImg = houseTour.getHouseImg();
        houseTourAndMoveIn.tourDate = houseTour.getTourDate();
        return houseTourAndMoveIn;
    }

    public static HouseTourAndMoveIn moveNullOf(HouseTour houseTour, MoveIn moveIn) {
        HouseTourAndMoveIn houseTourAndMoveIn = new HouseTourAndMoveIn();
        houseTourAndMoveIn.moveInHouseId = moveIn.getHouseId();
        houseTourAndMoveIn.moveInHouseName = moveIn.getHouseName();
        houseTourAndMoveIn.moveInHouseImg = moveIn.getHouseImg();
        houseTourAndMoveIn.moveInRoomName = moveIn.getRoomName();
        houseTourAndMoveIn.moveInDate = moveIn.getMoveDate();
        houseTourAndMoveIn.tourHouseId = houseTour.getHouseId();
        houseTourAndMoveIn.tourHouseName = houseTour.getHouseName();
        houseTourAndMoveIn.tourRoomName = houseTour.getRoomName();
        houseTourAndMoveIn.tourHouseImg = houseTour.getHouseImg();
        houseTourAndMoveIn.tourDate = houseTour.getTourDate();
        return houseTourAndMoveIn;
    }
}
