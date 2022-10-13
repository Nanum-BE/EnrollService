package com.nanum.enrollservice.housetour.infrastructure;

import com.nanum.common.HouseTourStatus;
import com.nanum.enrollservice.housetour.domain.HouseTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseTourRepository extends JpaRepository<HouseTour, Long> {
    boolean existsByUserIdAndRoomIdAndHouseTourStatusIn(Long userId, Long roomId, List<HouseTourStatus> houseTourStatus);
    List<HouseTour> findAllByUserId(Long userId);
    HouseTour findByUserId(Long userId);
}
