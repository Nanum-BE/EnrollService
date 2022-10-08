package com.nanum.enrollservice.housetour.presentation;

import com.nanum.common.BaseResponse;
import com.nanum.common.HouseTourStatus;
import com.nanum.common.Role;
import com.nanum.enrollservice.housetour.application.HouseTourService;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.dto.HouseTourUpdateDto;
import com.nanum.enrollservice.housetour.vo.HouseTourRequest;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.enrollservice.housetour.vo.HouseTourUpdateRequest;
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
import java.util.List;

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
    @PostMapping("/tours/houses/{houseId}/rooms/{roomId}")
    public ResponseEntity<Object> createHouseTour(@PathVariable Long houseId, @PathVariable Long roomId,
                                                  @Valid @RequestBody HouseTourRequest houseTourRequest) {

        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);
        houseTourService.createHouseTour(houseTourDto);

        String result = "하우스 투어 신청이 완료되었습니다.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    @Operation(summary = "하우스 투어 신청 목록 조회", description = "사용자가 하우스 투어 신청 목록을 조회하는 요청")
    @GetMapping("/users/{userId}/tours")
    public ResponseEntity<Object> retrieveHouseTour(@PathVariable Long userId) {
        List<HouseTourResponse> houseTourResponses = houseTourService.retrieveHouseTour(userId, Role.USER);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(houseTourResponses));
    }

    @Operation(summary = "하우스 투어 승인/거부/취소", description = "하우스 투어 신청을 승인/거부/취소하는 요청")
    @PutMapping("/tours")
    public ResponseEntity<Object> updateHouseTour(@Valid @RequestBody HouseTourUpdateRequest houseTourUpdateRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HouseTourUpdateDto houseTourUpdateDto = mapper.map(houseTourUpdateRequest, HouseTourUpdateDto.class);

        houseTourService.updateHouseTour(houseTourUpdateDto);
        String result = "";

        if(houseTourUpdateDto.getHouseTourStatus().equals(HouseTourStatus.APPROVED)) {
            result = "하우스 투어 신청이 승인되었습니다.";
        } else if(houseTourUpdateDto.getHouseTourStatus().equals(HouseTourStatus.REJECTED)) {
            result = "하우스 투어 신청이 거부되었습니다.";
        } else {
            result = "하우스 투어 신청이 취소되었습니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }

    //TODO #5 : 하우스 투어 완료 처리?


}
