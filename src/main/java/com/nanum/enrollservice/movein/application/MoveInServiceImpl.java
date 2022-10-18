package com.nanum.enrollservice.movein.application;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.movein.domain.MoveIn;
import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.dto.MoveInUpdateDto;
import com.nanum.enrollservice.movein.infrastructure.MoveInRepository;
import com.nanum.enrollservice.movein.vo.MoveInResponse;
import com.nanum.exception.DateException;
import com.nanum.exception.NotFoundException;
import com.nanum.exception.OverlapException;
import com.nanum.kafka.dto.KafkaRoomDto;
import com.nanum.kafka.messagequeue.KafkaProducerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoveInServiceImpl implements MoveInService {

    private final MoveInRepository moveInRepository;
    private final HouseTourRepository houseTourRepository;
    private final KafkaProducerImpl kafkaProducer;
    private final KafkaRoomDto kafkaRoomDto;

    @Override
    public void createMoveIn(MoveInDto moveInDto, Long userId) {

        MoveIn moveIn = moveInRepository.findFirstByHouseIdAndRoomIdOrderByUpdateAtDesc(moveInDto.getHouseId(), moveInDto.getRoomId());

        if (moveIn.getMoveInStatus() != null) {
            if (moveIn.getMoveInStatus().equals(MoveInStatus.WAITING) || moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACTING)
                    || moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACT_COMPLETED)) {
                throw new OverlapException("이미 신청이 완료된 방입니다.");
            }
        }

        if (!Objects.equals(houseTourRepository.getByUserIdAndRoomIdAndHouseId
                (userId, moveInDto.getRoomId(), moveInDto.getHouseId()), "TOUR_COMPLETED")) {
            throw new OverlapException("투어 신청이 완료되지 않은 방입니다");
        }
        List<MoveInStatus> moveInStatuses = List.of(MoveInStatus.WAITING, MoveInStatus.CONTRACTING);

        if (moveInRepository.existsByUserIdAndRoomIdAndMoveInStatusIn(userId, moveInDto.getRoomId(), moveInStatuses)) {
            throw new OverlapException("이미 신청이 완료된 방입니다.");
        }

        if (LocalDate.from(moveInDto.getMoveDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("입주 신청은 당일 예약이 불가능합니다.");
        } else if (moveInDto.getMoveDate().isBefore(LocalDate.now())) {
            throw new DateException("입주 날짜를 확인 해주세요.");
        }

        MoveIn toMoveIn = moveInDto.toEntity(userId);

        moveInRepository.save(toMoveIn);
    }

    @Override
    public List<MoveInResponse> retrieveMoveIn(Long userId) {

        List<MoveIn> moveIns = moveInRepository.findAllByUserId(userId);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return Arrays.asList(mapper.map(moveIns, MoveInResponse[].class));
    }

    @Override
    public void updateMoveIn(MoveInUpdateDto moveInUpdateDto) {
        MoveIn moveIn = moveInRepository.findById(moveInUpdateDto.getMoveInId()).orElse(null);

        if (moveIn == null) {
            throw new NotFoundException("해당 입주 신청 정보가 없습니다.");
        }

        switch (moveInUpdateDto.getMoveInStatus()) {
            case WAITING:
                throw new OverlapException("잘못된 요청입니다.");
            case CANCELED:
                if (moveIn.getMoveInStatus().equals(MoveInStatus.CANCELED)) {
                    throw new OverlapException("이미 처리된 요청입니다.");
                } else if (!moveIn.getMoveInStatus().equals(MoveInStatus.WAITING)) {
                    throw new OverlapException("취소할 수 없는 상태입니다.");
                }
                break;
            case CONTRACTING:
                kafkaProducer.send("house-topic",
                        KafkaRoomDto.builder()
                                .roomId(moveIn.getRoomId())
                                .message("contract")
                                .build());
            case REJECTED:
                if (!moveIn.getMoveInStatus().equals(MoveInStatus.WAITING)) {
                    if (moveIn.getMoveInStatus().equals(MoveInStatus.CANCELED)) {
                        throw new OverlapException("취소된 신청입니다.");
                    } else {
                        throw new OverlapException("이미 처리된 요청입니다.");
                    }
                }
                break;
            case CONTRACT_COMPLETED:
                if (!moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACTING)) {
                    if (moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACT_COMPLETED)) {
                        throw new OverlapException("이미 처리된 요청입니다");
                    }
                    throw new OverlapException("완료 처리할 수 없는 상태입니다");
                }
                kafkaProducer.send("house-topic",
                        KafkaRoomDto.builder()
                                .roomId(moveIn.getRoomId())
                                .message("completed")
                                .build());
                break;
        }

        MoveIn newMoveIn = moveInUpdateDto.toEntity(moveIn);
        moveInRepository.save(newMoveIn);
    }
}
