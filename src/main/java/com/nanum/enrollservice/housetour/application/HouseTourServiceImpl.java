package com.nanum.enrollservice.housetour.application;

import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.exception.DateException;
import com.nanum.exception.OverlapException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
