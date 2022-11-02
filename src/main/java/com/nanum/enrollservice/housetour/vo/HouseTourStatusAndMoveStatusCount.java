package com.nanum.enrollservice.housetour.vo;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourStatusAndMoveStatusCount {

    private Long MoveInWait;

    private Long MoveInProgress;

    private Long MoveInComplete;

    private Long TourWait;

    private Long TourProgress;

    private Long TourComplete;
}
