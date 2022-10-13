package com.nanum.enrollservice.movein.infrastructure;

import com.nanum.enrollservice.movein.domain.MoveIn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveInRepository extends JpaRepository<MoveIn, Long> {
}
