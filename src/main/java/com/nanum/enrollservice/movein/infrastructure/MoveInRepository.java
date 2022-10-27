package com.nanum.enrollservice.movein.infrastructure;

import com.nanum.common.MoveInStatus;
import com.nanum.enrollservice.movein.domain.MoveIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveInRepository extends JpaRepository<MoveIn, Long> {
    boolean existsByUserIdAndRoomIdAndMoveInStatusIn(Long userId, Long roomId, List<MoveInStatus> moveInStatus);
    List<MoveIn> findAllByUserId(Long userId);
    List<MoveIn> findAllByHostId(Long hostId);
    List<MoveIn> findAllByHouseIdAndMoveInStatus(Long houseId, MoveInStatus moveInStatus);
    MoveIn findByUserIdAndMoveInStatus(Long userId, MoveInStatus moveInStatus);
    MoveIn findFirstByHouseIdAndRoomIdOrderByUpdateAtDesc(Long houseId, Long roomId);
}
