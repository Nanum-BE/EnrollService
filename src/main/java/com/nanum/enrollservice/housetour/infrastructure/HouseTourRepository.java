package com.nanum.enrollservice.housetour.infrastructure;

import com.nanum.enrollservice.housetour.domain.HouseTour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseTourRepository extends JpaRepository<HouseTour, Long> {
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);
}
