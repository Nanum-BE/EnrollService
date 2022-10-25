package com.nanum.enrollservice.movein.vo;

import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.UserResponse;
import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInHouseResponse {

    private Long userId;
    private String nickName;
    private String profileImgUrl;

    public static UserInHouseResponse of(FeignResponse<UserResponse> responses) {
        UserInHouseResponse userInHouseResponse = new UserInHouseResponse();
        userInHouseResponse.userId = responses.getResult().getId();
        userInHouseResponse.nickName = responses.getResult().getNickName();
        userInHouseResponse.profileImgUrl = responses.getResult().getProfileImgUrl();

        return userInHouseResponse;
    }
}
