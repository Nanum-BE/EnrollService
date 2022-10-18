package com.nanum.enrollservice.housetour.presentation;

import com.nanum.common.BaseResponse;
import com.nanum.common.HouseTourStatus;
import com.nanum.common.Role;
import com.nanum.enrollservice.housetour.application.HouseTourService;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.dto.HouseTourUpdateDto;
import com.nanum.enrollservice.housetour.vo.HouseTourRequest;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.enrollservice.housetour.vo.HouseTourTimeResponse;
import com.nanum.enrollservice.housetour.vo.HouseTourUpdateRequest;
import com.nanum.exception.ExceptionResponse;
import com.nanum.utils.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "하우스 투어 신청", description = "하우스 투어 신청 관련 api")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "201", description = "created successfully", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
})
public class HouseTourController {

    private final HouseTourService houseTourService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "하우스 투어 신청 API", description = "사용자가 하우스 투어 신청을 하는 요청")
    @PostMapping("/tours/houses/{houseId}/rooms/{roomId}")
    public ResponseEntity<Object> createHouseTour(@PathVariable Long houseId, @PathVariable Long roomId,
                                                  @Valid @RequestBody HouseTourRequest houseTourRequest) {
        String token = jwtTokenProvider.customResolveToken();
        log.info(token);
        boolean isValid = jwtTokenProvider.isJwtValid(token);
        if (!isValid) {
            throw new ValidationException("토큰 유효성 검증에 실패했습니다.");
        }
        Long userId = jwtTokenProvider.getUserPk(token);
        log.info(String.valueOf(userId));
        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);
        houseTourService.createHouseTour(houseTourDto, userId);

        String result = "하우스 투어 신청이 완료되었습니다.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    @Operation(summary = "하우스 투어 신청 목록 조회", description = "사용자가 하우스 투어 신청 목록을 조회하는 요청")
    @GetMapping("/users/{userId}/tours")
    public ResponseEntity<Object> retrieveHouseTour(@PathVariable Long userId) {
        List<HouseTourResponse> houseTourResponses = houseTourService.retrieveHouseTour(userId, Role.USER);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(houseTourResponses));
    }

    @Operation(summary = "하우스 투어 승인/거부/취소/완료", description = "하우스 투어 신청을 승인/거부/취소/완료하는 요청")
    @PutMapping("/tours")
    public ResponseEntity<Object> updateHouseTour(@Valid @RequestBody HouseTourUpdateRequest houseTourUpdateRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        HouseTourUpdateDto houseTourUpdateDto = mapper.map(houseTourUpdateRequest, HouseTourUpdateDto.class);

        houseTourService.updateHouseTour(houseTourUpdateDto);
        String result = "";

        if (houseTourUpdateDto.getHouseTourStatus().equals(HouseTourStatus.APPROVED)) {
            result = "하우스 투어 신청이 승인되었습니다.";
        } else if (houseTourUpdateDto.getHouseTourStatus().equals(HouseTourStatus.REJECTED)) {
            result = "하우스 투어 신청이 거부되었습니다.";
        } else if (houseTourUpdateDto.getHouseTourStatus().equals(HouseTourStatus.TOUR_COMPLETED)) {
            result = "하우스 투어가 완료되었습니다.";
        } else {
            result = "하우스 투어 신청이 취소되었습니다.";
        }
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }

    @Operation(summary = "투어 신청시 날짜 선택했을 경우", description = "날짜를 선택하면 시간들에 대해 반환")
    @GetMapping("/tours/houses/{houseId}/date/{date}")
    public ResponseEntity<Object> retrieveTime(@PathVariable Long houseId,
                                               @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<HouseTourTimeResponse> houseTourTimeResponses = houseTourService.retrieveTourTime(houseId, date);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(houseTourTimeResponses));
    }
}
