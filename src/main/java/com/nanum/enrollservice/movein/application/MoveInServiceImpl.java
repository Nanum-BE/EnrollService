package com.nanum.enrollservice.movein.application;

import com.nanum.common.HouseTourStatus;
import com.nanum.common.MoveInStatus;
import com.nanum.common.Role;
import com.nanum.enrollservice.client.HouseServiceClient;
import com.nanum.enrollservice.client.UserServiceClient;
import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.HostRoomResponse;
import com.nanum.enrollservice.client.vo.HouseResponse;
import com.nanum.enrollservice.client.vo.UserResponse;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.movein.domain.MoveIn;
import com.nanum.enrollservice.movein.dto.MoveInDto;
import com.nanum.enrollservice.movein.dto.MoveInUpdateDto;
import com.nanum.enrollservice.movein.infrastructure.MoveInRepository;
import com.nanum.enrollservice.movein.vo.MoveInCompleteHouseResponse;
import com.nanum.enrollservice.movein.vo.MoveInResponse;
import com.nanum.enrollservice.movein.vo.UserInHouseResponse;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoveInServiceImpl implements MoveInService {

    private final MoveInRepository moveInRepository;
    private final HouseTourRepository houseTourRepository;
    private final KafkaProducerImpl kafkaProducer;
    private final HouseServiceClient houseServiceClient;
    private final UserServiceClient userServiceClient;

    @Override
    public void createMoveIn(MoveInDto moveInDto, Long userId) {
        FeignResponse<HouseResponse> houseDetails = houseServiceClient.getHouseDetails(moveInDto.getHouseId());
        HostRoomResponse result = houseServiceClient.getRoomDetails(moveInDto.getHouseId(), moveInDto.getRoomId()).getResult();
        MoveIn moveIn = moveInRepository.findFirstByHouseIdAndRoomIdOrderByUpdateAtDesc(moveInDto.getHouseId(), moveInDto.getRoomId());
        List<MoveInStatus> moveInStatuses = List.of(MoveInStatus.WAITING, MoveInStatus.CONTRACTING);

        if (moveIn != null) {
            if (!Objects.equals(houseTourRepository.findFirstByHouseIdAndRoomIdAndUserIdOrderByUpdateAtDesc(moveInDto.getHouseId(),
                    moveInDto.getRoomId(), userId).getHouseTourStatus(), HouseTourStatus.TOUR_COMPLETED)) {
                throw new OverlapException("?????? ????????? ???????????? ?????? ????????????");
            } else if (moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACTING) || moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACT_COMPLETED)) {
                throw new OverlapException("?????? ??????????????? ????????? ???????????? ????????????.");
            } else if (moveInRepository.existsByUserIdAndRoomIdAndMoveInStatusIn(userId, moveInDto.getRoomId(), moveInStatuses)) {
                throw new OverlapException("?????? ????????? ????????? ????????????.");
            } else if (LocalDate.from(moveInDto.getMoveDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
                throw new DateException("?????? ????????? ?????? ????????? ??????????????????.");
            } else if (moveInDto.getMoveDate().isBefore(LocalDate.now())) {
                throw new DateException("?????? ????????? ?????? ????????????.");
            }
        } else {
            if (!Objects.equals(houseTourRepository.findFirstByHouseIdAndRoomIdAndUserIdOrderByUpdateAtDesc(moveInDto.getHouseId(),
                    moveInDto.getRoomId(), userId).getHouseTourStatus(), HouseTourStatus.TOUR_COMPLETED)) {
                throw new OverlapException("?????? ????????? ???????????? ?????? ????????????");
            }

            if (moveInRepository.existsByUserIdAndRoomIdAndMoveInStatusIn(userId, moveInDto.getRoomId(), moveInStatuses)) {
                throw new OverlapException("?????? ????????? ????????? ????????????.");
            }

            if (LocalDate.from(moveInDto.getMoveDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
                throw new DateException("?????? ????????? ?????? ????????? ??????????????????.");
            } else if (moveInDto.getMoveDate().isBefore(LocalDate.now())) {
                throw new DateException("?????? ????????? ?????? ????????????.");
            }

        }

        MoveIn toMoveIn = moveInDto.toEntity(userId, houseDetails.getResult().getHostId(), houseDetails.getResult().getHouseName(),
                houseDetails.getResult().getHouseMainImg(), houseDetails.getResult().getStreetAddress(),
                houseDetails.getResult().getDetailAddress(), houseDetails.getResult().getLotAddress(),
                houseDetails.getResult().getZipCode(), result.getRoom().getName());

        moveInRepository.save(toMoveIn);

    }

    @Transactional(readOnly = true)
    @Override
    public List<MoveInResponse> retrieveMoveIn(Long id, Role role) {
        List<MoveIn> moveIns = null;
        if (role.equals(Role.USER)) {
            moveIns = moveInRepository.findAllByUserId(id);
        }
        if (role.equals(Role.HOST)) {
            moveIns = moveInRepository.findAllByHostId(id);
        }
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return Arrays.asList(mapper.map(moveIns, MoveInResponse[].class));
    }

    @Override
    public void updateMoveIn(MoveInUpdateDto moveInUpdateDto) {
        MoveIn moveIn = moveInRepository.findById(moveInUpdateDto.getMoveInId()).orElse(null);

        if (moveIn == null) {
            throw new NotFoundException("?????? ?????? ?????? ????????? ????????????.");
        }

        switch (moveInUpdateDto.getMoveInStatus()) {
            case WAITING:
                throw new OverlapException("????????? ???????????????.");
            case CANCELED:
                if (moveIn.getMoveInStatus().equals(MoveInStatus.CANCELED)) {
                    throw new OverlapException("?????? ????????? ???????????????.");
                } else if (!moveIn.getMoveInStatus().equals(MoveInStatus.WAITING)) {
                    throw new OverlapException("????????? ??? ?????? ???????????????.");
                }
                break;
            case CONTRACTING:
                kafkaProducer.send("house-topic",
                        KafkaRoomDto.builder()
                                .roomId(moveIn.getRoomId())
                                .message("contract")
                                .build());
                if (moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACTING)){
                    throw new OverlapException("?????? ????????? ???????????????!");
                }
                userServiceClient.sendMoveInApproveSMS(moveIn.getUserId());
                break;
            case REJECTED:
                if (!moveIn.getMoveInStatus().equals(MoveInStatus.WAITING)) {
                    if (moveIn.getMoveInStatus().equals(MoveInStatus.CANCELED)) {
                        throw new OverlapException("????????? ???????????????.");
                    } else if (moveIn.getMoveInStatus().equals(MoveInStatus.REJECTED)){
                        throw new OverlapException("?????? ????????? ???????????????.");
                    } else
                        throw new OverlapException("?????? ????????? ???????????????.");
                }
                userServiceClient.sendMoveInRejectSMS(moveIn.getUserId());
                break;
            case CONTRACT_COMPLETED:
                if (!moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACTING)) {
                    if (moveIn.getMoveInStatus().equals(MoveInStatus.CONTRACT_COMPLETED)) {
                        throw new OverlapException("?????? ????????? ???????????????");
                    }
                    throw new OverlapException("?????? ????????? ??? ?????? ???????????????");
                }
                KafkaRoomDto kafkaRoomDto = KafkaRoomDto.builder()
                        .roomId(moveIn.getRoomId())
                        .message("completed")
                        .endDate(moveInUpdateDto.getExpireDate())
                        .build();
                kafkaProducer.send("house-topic",
                        kafkaRoomDto);
                userServiceClient.sendMoveInCompleteSMS(moveIn.getUserId());
                if (!moveIn.getMoveDate().isBefore(moveInUpdateDto.getExpireDate())) {
                    throw new DateException("????????? ??????????????????.");
                }
                break;
        }

        MoveIn newMoveIn = moveInUpdateDto.toEntity(moveIn, moveInUpdateDto.getExpireDate());
        moveInRepository.save(newMoveIn);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserInHouseResponse> retrieveUserInHouse(Long houseId) {
        List<MoveIn> moveInList = moveInRepository.findAllByHouseIdAndMoveInStatus(houseId, MoveInStatus.CONTRACT_COMPLETED);
        List<FeignResponse<UserResponse>> responses = moveInList
                .stream()
                .map(moveIn -> userServiceClient.getUser(moveIn.getUserId()))
                .collect(Collectors.toList());

        return responses.stream()
                .map(UserInHouseResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public MoveInCompleteHouseResponse retrieveHouseByInUser(Long userId) {
        MoveIn moveIn = moveInRepository.findByUserIdAndMoveInStatus(userId, MoveInStatus.CONTRACT_COMPLETED);

        if (moveIn == null) {
            throw new NotFoundException("?????? ????????? ???????????? ????????????");
        }

        List<MoveIn> moveInList = moveInRepository.findAllByHouseIdAndMoveInStatus(moveIn.getHouseId(), MoveInStatus.CONTRACT_COMPLETED);
        List<Long> userIds = moveInList
                .stream()
                .map(MoveIn::getUserId)
                .collect(Collectors.toList());

        return new MoveInCompleteHouseResponse(moveIn, userIds);
    }

}
