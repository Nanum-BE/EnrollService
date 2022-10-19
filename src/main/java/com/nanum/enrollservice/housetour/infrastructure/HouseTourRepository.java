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
    List<HouseTour> findAllByHouseIdAndTourDate(Long houseId, LocalDate date);
    @Query(value = "select h.houseTourStatus from HouseTour h where h.userId=:userId " +
            "and h.roomId=:roomId and h.houseId=:houseId")
    String getByUserIdAndRoomIdAndHouseId(Long userId, Long roomId, Long houseId);
}
