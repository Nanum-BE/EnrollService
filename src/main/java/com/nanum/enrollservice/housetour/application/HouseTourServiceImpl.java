package com.nanum.enrollservice.housetour.application;

import com.nanum.common.HouseTourStatus;
import com.nanum.common.Role;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.dto.HouseTourUpdateDto;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.exception.DateException;
import com.nanum.exception.NotFoundException;
import com.nanum.exception.OverlapException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseTourServiceImpl implements HouseTourService {
    private final HouseTourRepository houseTourRepository;

    @Override
    public void createHouseTour(HouseTourDto houseTourDto) {
        List<HouseTourStatus> houseTourStatuses = List.of(HouseTourStatus.WAITING, HouseTourStatus.APPROVED);

        if (houseTourRepository.existsByUserIdAndRoomIdAndHouseTourStatusIn(houseTourDto.getUserId(),
                houseTourDto.getRoomId(),
                houseTourStatuses)) {
            throw new OverlapException("이미 신청된 방입니다.");
        }

        if (LocalDate.from(houseTourDto.getTourDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("투어 신청은 당일 예약이 불가능합니다.");
        } else if (houseTourDto.getTourDate().isBefore(LocalDateTime.now())) {
            throw new DateException("투어 날짜를 확인 해주세요.");
        }

        HouseTour houseTour = houseTourDto.dtoToEntity();
        houseTourRepository.save(houseTour);
    }

    @Override
    public List<HouseTourResponse> retrieveHouseTour(Long id, Role role) {

        List<HouseTour> houseTours = new ArrayList<>();

        if (role.equals(Role.USER)) {
            houseTours = houseTourRepository.findAllByUserId(id);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return Arrays.asList(mapper.map(houseTours, HouseTourResponse[].class));
    }

    @Override
    public void updateHouseTour(HouseTourUpdateDto houseTourUpdateDto) {
        HouseTour houseTour = houseTourRepository.findById(houseTourUpdateDto.getHouseTourId()).orElse(null);

        if (houseTour == null) {
            throw new NotFoundException("해당 투어 신청 정보가 없습니다.");
        }

        switch (houseTourUpdateDto.getHouseTourStatus()) {
            case CANCELED:
                if (houseTour.getHouseTourStatus().equals(HouseTourStatus.CANCELED)) {
                    throw new OverlapException("이미 처리된 요청입니다.");
                } else if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.WAITING)) {
                    throw new OverlapException("취소할 수 없는 상태입니다.");
                }
                break;
            case APPROVED:
            case REJECTED:
                if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.WAITING)) {
                    if(houseTour.getHouseTourStatus().equals(HouseTourStatus.CANCELED)) {
                        throw new OverlapException("취소된 신청입니다.");
                    } else {
                        throw new OverlapException("이미 처리된 요청입니다.");
                    }
                }
                break;
            case TOUR_COMPLETED:
                if (!houseTour.getHouseTourStatus().equals(HouseTourStatus.APPROVED)) {
                    if(houseTour.getHouseTourStatus().equals(HouseTourStatus.TOUR_COMPLETED)) {
                        throw new OverlapException("이미 처리된 요청입니다.");
                    }
                    throw new OverlapException("완료 처리할 수 없는 상태입니다.");
                }
                break;
        }

        HouseTour newHouseTour = houseTourUpdateDto.toEntity(houseTour);
        houseTourRepository.save(newHouseTour);
    }
}
