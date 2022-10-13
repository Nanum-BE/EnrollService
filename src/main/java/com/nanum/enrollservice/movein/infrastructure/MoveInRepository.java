package com.nanum.enrollservice.movein.infrastructure;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveInRepository extends JpaRepository<MoveIn, Long> {
    boolean existsByUserIdAndRoomIdAndMoveInStatusIn(Long userId, Long roomId, List<MoveInStatus> moveInStatus);
    List<MoveIn> findAllByUserId(Long userId);
}
