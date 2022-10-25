package com.nanum.enrollservice.housetour.application;

import com.nanum.common.HouseTourStatus;
import com.nanum.common.Role;
import com.nanum.common.RoomStatus;
import com.nanum.enrollservice.client.HouseServiceClient;
import com.nanum.enrollservice.client.UserServiceClient;
import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.HostRoomResponse;
import com.nanum.enrollservice.client.vo.HouseResponse;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.dto.HouseTourUpdateDto;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourTimeRepository;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.enrollservice.housetour.vo.HouseTourTimeResponse;
import com.nanum.exception.DateException;
import com.nanum.exception.NotFoundException;
import com.nanum.exception.OverlapException;
import com.nanum.kafka.dto.KafkaUserDto;
import com.nanum.kafka.messagequeue.KafkaProducerImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseTourServiceImpl implements HouseTourService {
    private final HouseTourRepository houseTourRepository;
    private final HouseTourTimeRepository houseTourTimeRepository;
    private final HouseServiceClient houseServiceClient;
    private final UserServiceClient userServiceClient;

    @Override
    public void createHouseTour(HouseTourDto houseTourDto, Long userId) {
        List<HouseTourStatus> houseTourStatuses = List.of(HouseTourStatus.WAITING, HouseTourStatus.APPROVED);
        FeignResponse<HostRoomResponse> roomDetails = houseServiceClient.getRoomDetails(houseTourDto.getHouseId(), houseTourDto.getRoomId());
        FeignResponse<HouseResponse> houseDetails = houseServiceClient.getHouseDetails(houseTourDto.getHouseId());

        if (!roomDetails.getResult().getRoom().getStatus().equals(RoomStatus.WAITING)) {
            throw new OverlapException("투어 신청 불가능한 방입니다");
        }

        if (houseTourRepository.existsByUserIdAndRoomIdAndHouseTourStatusIn(userId,
                houseTourDto.getRoomId(),
                houseTourStatuses)) {
            throw new OverlapException("이미 신청된 방입니다.");
        }

        if (LocalDate.from(houseTourDto.getTourDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("투어 신청은 당일 예약이 불가능합니다.");
        } else if (houseTourDto.getTourDate().isBefore(LocalDate.now())) {
            throw new DateException("투어 날짜를 확인 해주세요.");
        }

        HouseTourTime houseTourTime = houseTourTimeRepository.findById(houseTourDto.getTimeId()).get();

        HouseTour houseTour = houseTourDto.dtoToEntity(houseTourTime, userId, houseDetails.getResult().getHostId()
                , houseDetails.getResult().getHouseName(), roomDetails.getResult().getRoom().getName(),
                houseDetails.getResult().getHouseMainImg(), houseDetails.getResult().getStreetAddress(),
                houseDetails.getResult().getLotAddress(), houseDetails.getResult().getDetailAddress(),
                houseDetails.getResult().getZipCode());

        houseTourRepository.save(houseTour);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HouseTourResponse> retrieveHouseTour(Long id, Role role) {

        List<HouseTour> tours = new ArrayList<>();
        if (role.equals(Role.USER)) {
            tours = houseTourRepository.findAllByUserId(id);
        }
        if (role.equals(Role.HOST)) {
            tours = houseTourRepository.findAllByHostId(id);
        }

        if (tours.isEmpty()) {
            throw new NotFoundException("예약된 투어 신청이 없습니다");
        }

        return tours.stream()
                .map(HouseTourResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public void updateHouseTour(HouseTourUpdateDto houseTourUpdateDto) {
        HouseTour houseTour = houseTourRepository.findById(houseTourUpdateDto.getHouseTourId()).orElse(null);

        if (houseTour == null) {
            throw new NotFoundException("해당 투어 신청 정보가 없습니다.");
        }

        switch (houseTourUpdateDto.getHouseTourStatus()) {
            case WAITING:
                throw new OverlapException("잘못된 요청입니다.");
            case CANCELED:
                if (houseTour.getHouseTourStatus().equals(HouseTourStatus.CANCELED)) {
                    throw new OverlapException("이미 처리된 요청입니다.");
                } else if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.WAITING)) {
                    throw new OverlapException("취소할 수 없는 상태입니다.");
                }
                break;
            case APPROVED:
                if (houseTour.getHouseTourStatus().equals(HouseTourStatus.APPROVED)) {
                    throw new OverlapException("이미 처리된 요청입니다");
                }
                userServiceClient.sendTourApproveSMS(houseTour.getUserId());
                break;
            case REJECTED:
                if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.WAITING)) {
                    if (houseTour.getHouseTourStatus().equals(HouseTourStatus.CANCELED)) {
                        throw new OverlapException("취소된 신청입니다.");
                    } else if (houseTour.getHouseTourStatus().equals(HouseTourStatus.REJECTED)){
                        throw new OverlapException("이미 처리된 요청입니다.");
                    } else
                        throw new OverlapException("이미 처리된 요청입니다");
                }
                userServiceClient.sendTourRejectSMS(houseTour.getUserId());
                break;
            case TOUR_COMPLETED:
                if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.APPROVED)) {
                    if (houseTour.getHouseTourStatus().equals(HouseTourStatus.TOUR_COMPLETED)) {
                        throw new OverlapException("이미 처리된 요청입니다.");
                    }
                    throw new OverlapException("완료 처리할 수 없는 상태입니다.");
                }
                userServiceClient.sendTourCompleteSMS(houseTour.getUserId());
                break;
        }

        HouseTour newHouseTour = houseTourUpdateDto.toEntity(houseTour);
        houseTourRepository.save(newHouseTour);
    }

    @Override
    public List<HouseTourTimeResponse> retrieveTourTime(Long houseId, Long roomId, LocalDate date) {

        List<HouseTourTime> timeList = houseTourTimeRepository.findAll();
        List<HouseTour> tours = houseTourRepository.findAllByHouseIdAndRoomIdAndTourDate(houseId, roomId, date);

        List<HouseTourTimeResponse> houseTourTimeResponses = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();

        tours.forEach(houseTour -> {
            map.put(houseTour.getHouseTourTime().getId(), houseTour.getHouseTourStatus());
        });

        timeList.forEach(houseTourTime -> {
            boolean isPossible = true;
            if (map.containsKey(houseTourTime.getId())) {
                isPossible = map.get(houseTourTime.getId()).equals(HouseTourStatus.CANCELED)
                        || map.get(houseTourTime.getId()).equals(HouseTourStatus.REJECTED);
            }
            houseTourTimeResponses.add(HouseTourTimeResponse.builder()
                    .timeId(houseTourTime.getId())
                    .time(houseTourTime.getTime().toString().substring(0, 5))
                    .isAvailable(isPossible)
                    .build());
        });
        return houseTourTimeResponses;
    }

}
