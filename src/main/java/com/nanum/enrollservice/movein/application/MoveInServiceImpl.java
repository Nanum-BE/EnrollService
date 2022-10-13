package com.nanum.enrollservice.movein.application;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.infrastructure.MoveInRepository;
import com.nanum.exception.DateException;
import com.nanum.exception.OverlapException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MoveInServiceImpl implements MoveInService{

    private final MoveInRepository moveInRepository;

    @Override
    public void createMoveIn(MoveInDto moveInDto) {

        List<MoveInStatus> moveInStatuses = List.of(MoveInStatus.WAITING, MoveInStatus.CONTRACTING);

        if(moveInRepository.existsByUserIdAndRoomIdAndMoveInStatusIn(moveInDto.getUserId(), moveInDto.getRoomId(), moveInStatuses)) {
            throw new OverlapException("이미 신청된 방입니다.");
        }

        if (LocalDate.from(moveInDto.getMoveDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("입주 신청은 당일 예약이 불가능합니다.");
        } else if (moveInDto.getMoveDate().isBefore(LocalDateTime.now())) {
            throw new DateException("입주 날짜를 확인 해주세요.");
        }

        MoveIn moveIn = moveInDto.toEntity();

        moveInRepository.save(moveIn);
    }
}
