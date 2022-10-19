package com.nanum.enrollservice.movein.application;

import com.nanum.common.Role;
import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.dto.MoveInUpdateDto;
import com.nanum.enrollservice.movein.vo.MoveInResponse;
import com.nanum.enrollservice.movein.vo.UserInHouseResponse;

import java.util.List;

public interface MoveInService {
    void createMoveIn(MoveInDto moveInDto, Long userId);
    List<MoveInResponse> retrieveMoveIn(Long id, Role role);
    void updateMoveIn(MoveInUpdateDto moveInUpdateDto);
    List<UserInHouseResponse> retrieveUserInHouse(Long houseId);
}
