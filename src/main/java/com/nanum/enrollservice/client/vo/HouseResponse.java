package com.nanum.enrollservice.client.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseResponse {

    @Schema(description = "하우스 식별자")
    private Long id;

    @Schema(description = "하우스의 호스트 ID")
    private Long hostId;

    @Schema(description = "하우스 이름")
    private String houseName;

    @Schema(description = "도로명 주소")
    private String streetAddress;

    @Schema(description = "지번 주소")
    private String lotAddress;

    @Schema(description = "건물 형태")
    private String houseType;

    @Schema(description = "상세 주소")
    private String detailAddress;

    @Schema(description = "하우스 메인 이미지 경로")
    private String houseMainImg;

    @Schema(description = "우편번호")
    private String zipCode;


}