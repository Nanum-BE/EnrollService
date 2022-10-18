//package com.nanum.enrollservice.housetour.presentation;
//
//import com.nanum.enrollservice.housetour.application.HouseTourService;
//import com.nanum.enrollservice.housetour.domain.HouseTour;
//import com.nanum.enrollservice.housetour.dto.HouseTourDto;
//import com.nanum.enrollservice.housetour.infrastructure.HouseTourRepository;
//import com.nanum.enrollservice.housetour.vo.HouseTourRequest;
//import org.junit.jupiter.api.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@SpringBootTest
//@Transactional
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class HouseTourControllerTest {
//
//    Logger log = (Logger) LoggerFactory.getLogger(HouseTourControllerTest.class);
//
//    @Autowired
//    HouseTourService houseTourService;
//
//    @Autowired
//    HouseTourRepository houseTourRepository;
//
//    @BeforeEach
//    void setUp() {
//        log.info("===== 테스트 시작 =====");
//    }
//
//    @AfterEach
//    void tearDown() {
//        log.info("===== 테스트 종료 =====");
//    }
//
//    @Test
//    @DisplayName("하우스 투어 신청")
//    @Order(1)
//    void createHouseTour() {
//        // given
//        Long houseId = 1L;
//        Long roomId = 1L;
//
//        HouseTourRequest houseTourRequest = new HouseTourRequest(100L, LocalDate.parse("2022-10-14T20:00:00"), 1L, "저녁 시간대에도 투어 가능할까요?");
//        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);
//
//        // when
//        houseTourService.createHouseTour(houseTourDto);
//
//        // then
//        HouseTour houseTour = houseTourRepository.findByUserId(100L);
//        log.info("Id: {}", houseTour.getId());
//        log.info("HouseId: {}", houseTour.getHouseId());
//        log.info("RoomId: {}", houseTour.getRoomId());
//        log.info("UserId: {}", houseTour.getUserId());
//        log.info("TourDate: {}", houseTour.getTourDate());
//        log.info("Inquiry: {}", houseTour.getInquiry());
//        log.info("HouseTourStatus: {}", houseTour.getHouseTourStatus());
//    }
//
//    @Test
//    @DisplayName("하우스 투어 신청 날짜 예외")
//    @Order(2)
//    void createHouseTourDateException() {
//        // given
//        Long houseId = 1L;
//        Long roomId = 1L;
//
//        HouseTourRequest houseTourRequest = new HouseTourRequest(100L, LocalDate.parse("2022-09-14T20:00:00"), 1L, "저녁 시간대에도 투어 가능할까요?");
//        HouseTourDto houseTourDto = houseTourRequest.toHouseTourDto(houseId, roomId);
//
//        // when
//        houseTourService.createHouseTour(houseTourDto);
//
//        // then
//        HouseTour houseTour = houseTourRepository.findByUserId(100L);
//        log.info("Id: {}", houseTour.getId());
//        log.info("HouseId: {}", houseTour.getHouseId());
//        log.info("RoomId: {}", houseTour.getRoomId());
//        log.info("UserId: {}", houseTour.getUserId());
//        log.info("TourDate: {}", houseTour.getTourDate());
//        log.info("Inquiry: {}", houseTour.getInquiry());
//        log.info("HouseTourStatus: {}", houseTour.getHouseTourStatus());
//    }
//}