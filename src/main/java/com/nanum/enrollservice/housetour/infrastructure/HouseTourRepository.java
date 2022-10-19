package com.nanum.enrollservice.housetour.infrastructure;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HouseTourRepository extends JpaRepository<HouseTour, Long> {
    boolean existsByUserIdAndRoomIdAndHouseTourStatusIn(Long userId, Long roomId, List<HouseTourStatus> houseTourStatus);
    List<HouseTour> findAllByUserId(Long userId);
    HouseTour findByUserId(Long userId);
    List<HouseTour> findAllByHouseIdAndRoomIdAndTourDate(Long houseId, Long roomId, LocalDate date);
}
