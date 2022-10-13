package com.nanum.enrollservice.movein.application;

import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.vo.MoveInResponse;

import java.util.List;

public interface MoveInService {
    void createMoveIn(MoveInDto moveInDto);
    List<MoveInResponse> retrieveMoveIn(Long userId);
}
