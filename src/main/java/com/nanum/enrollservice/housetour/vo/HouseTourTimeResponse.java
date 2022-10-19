package com.nanum.enrollservice.housetour.vo;

import lombok.*;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourTimeResponse {
    private Long timeId;
    private String time;
    private Boolean isAvailable;

}
