package com.nanum.enrollservice.housetour.presentation;

import com.nanum.common.BaseResponse;
import com.nanum.enrollservice.housetour.application.HouseTourService;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.vo.HouseTourRequest;
import com.nanum.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "하우스 투어 신청", description = "하우스 투어 신청 관련 api")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "201", description = "created successfully", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
})
public class HouseTourController {

    private final HouseTourService houseTourService;

    @Operation(summary = "하우스 투어 신청 API", description = "사용자가 하우스 투어 신청을 하는 요청")
    @PostMapping("/houses/{houseId}/rooms/{roomId}/tour")
    public ResponseEntity<Object> createHouseTour(@PathVariable Long houseId, @PathVariable Long roomId,
                                                  @Valid @RequestBody HouseTourRequest houseTourRequest) {

        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);
        houseTourService.createHouseTour(houseTourDto);

        String result = "하우스 투어 신청이 완료되었습니다.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    //TODO #2 : 하우스 투어 신청 상태 조회 (사용자, 호스트)


    //TODO #3 : 하우스 투어 승인/거부


    //TODO #4 : 하우스 투어 취소


    //TODO #5 : 하우스 투어 완료 처리?
}
