package com.nanum.enrollservice.movein.vo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInHouseResponse {
    private Long userId;
    private String nickName;
    private String profileImgUrl;
}
