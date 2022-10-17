package com.nanum.enrollservice.housetour;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
import com.nanum.enrollservice.housetour.infrastructure.HouseTourTimeRepository;
import com.nanum.enrollservice.housetour.vo.HouseTourRequest;
import com.nanum.exception.DateException;
import com.nanum.exception.OverlapException;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class HouseTourServiceImplTest {
    Logger log = (Logger) LoggerFactory.getLogger(HouseTourServiceImplTest.class);

    @Autowired
    HouseTourRepository houseTourRepository;

    @Autowired
    HouseTourTimeRepository houseTourTimeRepository;

    @BeforeEach
    void setUp() {
        log.info("===== 테스트 시작 =====");
    }

    @AfterEach
    void tearDown() {
        log.info("===== 테스트 종료 =====");
    }

    @Test
    @DisplayName("하우스 투어 신청")
    @Transactional
    @Order(1)
    void createHouseTour() {
        // given
        Long houseId = 1L;
        Long roomId = 1L;

        HouseTourRequest houseTourRequest = new HouseTourRequest(1L, LocalDate.parse("2022-10-14T20:00:00"), 1L,"저녁 시간대에도 투어 가능할까요?");
        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);

        // when
        List<HouseTourStatus> houseTourStatuses = List.of(HouseTourStatus.WAITING, HouseTourStatus.APPROVED);

        if (houseTourRepository.existsByUserIdAndRoomIdAndHouseTourStatusIn(houseTourDto.getUserId(),
                houseTourDto.getRoomId(),
                houseTourStatuses)) {
            throw new OverlapException("이미 신청된 방입니다.");
        }

        if (LocalDate.from(houseTourDto.getTourDate()).isEqual(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("투어 신청은 당일 예약이 불가능합니다.");
        } else if (houseTourDto.getTourDate().isBefore(LocalDate.from(LocalDateTime.now()))) {
            throw new DateException("투어 날짜를 확인 해주세요.");
        }

        HouseTourTime houseTourTime = houseTourTimeRepository.findById(houseTourDto.getTimeId()).get();

        HouseTour houseTour = houseTourDto.dtoToEntity(houseTourTime);
        HouseTour savedTour = houseTourRepository.save(houseTour);

        // then
        Assertions.assertEquals(houseTour, houseTourRepository.findById(savedTour.getId()));
    }

    @Test
    @Order(2)
    void retrieveHouseTour() {
    }

    @Test
    @Order(3)
    void updateHouseTour() {
    }
}