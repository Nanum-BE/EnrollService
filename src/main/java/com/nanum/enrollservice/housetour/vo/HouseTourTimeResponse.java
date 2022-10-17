package com.nanum.enrollservice.housetour.vo;

import lombok.*;

import java.sql.Time;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseTourTimeResponse {
    private Long timeId;
    private Time time;
    private Boolean isAvailable;

}
