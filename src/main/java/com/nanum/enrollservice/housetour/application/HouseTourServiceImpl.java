package com.nanum.enrollservice.housetour.application;

import com.nanum.common.Role;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.exception.DateException;
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
public class HouseTourServiceImpl implements HouseTourService{
    private final HouseTourRepository houseTourRepository;

    @Override
    public void createHouseTour(HouseTourDto houseTourDto) {

        if (houseTourRepository.existsByUserIdAndRoomId(houseTourDto.getUserId(), houseTourDto.getRoomId())) {
            throw new OverlapException("이미 신청된 방입니다.");
        }

        if (LocalDate.from(houseTourDto.getTourDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("투어 신청은 당일 예약이 불가능합니다.");
        } else if(houseTourDto.getTourDate().isBefore(LocalDateTime.now())) {
            throw new DateException("투어 날짜를 확인 해주세요.");
        }

        HouseTour houseTour = houseTourDto.dtoToEntity();
        houseTourRepository.save(houseTour);
    }

    @Override
    public List<HouseTourResponse> retrieveHouseTour(Long id, Role role) {

        List<HouseTour> houseTours = new ArrayList<>();

        if(role.equals(Role.USER)) {
            houseTours = houseTourRepository.findAllByUserId(id);
        }

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return Arrays.asList(mapper.map(houseTours, HouseTourResponse[].class));
    }
}
