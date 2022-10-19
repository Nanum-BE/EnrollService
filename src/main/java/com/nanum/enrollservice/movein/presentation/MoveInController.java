package com.nanum.enrollservice.movein.presentation;

import com.nanum.common.BaseResponse;
import com.nanum.common.MoveInStatus;
import com.nanum.common.Role;
import com.nanum.enrollservice.movein.application.MoveInService;
import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.dto.MoveInUpdateDto;
import com.nanum.enrollservice.movein.vo.MoveInRequest;
import com.nanum.enrollservice.movein.vo.MoveInResponse;
import com.nanum.enrollservice.movein.vo.MoveInUpdateRequest;
import com.nanum.enrollservice.movein.vo.UserInHouseResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "하우스 입주 신청", description = "하우스 입주 신청 관련 api")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "success", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "201", description = "created successfully", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
        @ApiResponse(responseCode = "400", description = "bad request", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
})
public class MoveInController {
    private final MoveInService moveInService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "하우스 입주 신청 API", description = "사용자가 하우스 입주 신청을 하는 요청")
    @PostMapping("/move-in/houses/{houseId}/rooms/{roomId}")
    public ResponseEntity<Object> createMoveIn(@PathVariable Long houseId, @PathVariable Long roomId,
                                               @Valid @RequestBody MoveInRequest moveInRequest) {
        String token = jwtTokenProvider.customResolveToken();
        System.out.println("token = " + token);
        boolean isValid = jwtTokenProvider.isJwtValid(token);
        if (!isValid) {
            throw new ValidationException("토큰 유효성 검증에 실패했습니다.");
        }
        Long userId = jwtTokenProvider.getUserPk(token);
        System.out.println("userId = " + userId);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MoveInDto moveInDto = mapper.map(moveInRequest, MoveInDto.class);
        moveInDto.setHouseId(houseId);
        moveInDto.setRoomId(roomId);
        moveInDto.setMoveInStatus(MoveInStatus.WAITING);

        moveInService.createMoveIn(moveInDto, userId);

        String result = "하우스 입주 신청이 완료되었습니다.";
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<>(result));
    }

    @Operation(summary = "사용자 하우스 입주 신청 조회 API", description = "사용자가 하우스 입주 신청 목록을 조회하는 요청")
    @GetMapping("/move-in/users/{userId}")
    public ResponseEntity<Object> retrieveMoveIn(@PathVariable Long userId) {
        List<MoveInResponse> moveInResponses = moveInService.retrieveMoveIn(userId, Role.USER);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(moveInResponses));
    }

    @Operation(summary = "호스트 하우스 입주 신청 조회 API", description = "호스트가 하우스 입주 신청 목록을 조회하는 요청")
    @GetMapping("/move-in/hosts/{hostId}")
    public ResponseEntity<Object> retrieveHostMoveIn(@PathVariable Long hostId) {
        List<MoveInResponse> moveInResponses = moveInService.retrieveMoveIn(hostId, Role.HOST);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(moveInResponses));
    }

    @Operation(summary = "호스트 하우스에 입주하는 사용자 조회 API", description = "호스트가 하우스에 입주중인 세입자 조회하는 요청")
    @GetMapping("/move-in/houses/{houseId}")
    public ResponseEntity<Object> retrieveHouseInUser(@PathVariable Long houseId) {
        List<UserInHouseResponse> userInHouseResponses = moveInService.retrieveUserInHouse(houseId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(userInHouseResponses));
    }

    @Operation(summary = "하우스 입주 신청 취소/승인/거부 API", description = "하우스 입주 신청을 취소/승인/거부하는 요청")
    @PutMapping("/move-in")
    public ResponseEntity<Object> updateMoveIn(@Valid @RequestBody MoveInUpdateRequest moveInUpdateRequest) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        MoveInUpdateDto moveInUpdateDto = mapper.map(moveInUpdateRequest, MoveInUpdateDto.class);
        moveInService.updateMoveIn(moveInUpdateDto);
        String result;

        if (moveInUpdateRequest.getMoveInStatus().equals(MoveInStatus.CONTRACTING)) {
            result = "하우스 입주 신청이 승인되었습니다.";
        } else if (moveInUpdateRequest.getMoveInStatus().equals(MoveInStatus.REJECTED)) {
            result = "하우스 입주 신청이 거부되었습니다.";
        } else if (moveInUpdateRequest.getMoveInStatus().equals(MoveInStatus.CONTRACT_COMPLETED)) {
            result = "하우스 입주 계약이 완료되었습니다.";
        } else {
            result = "하우스 입주 신청이 취소되었습니다.";
        }

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
    }

}