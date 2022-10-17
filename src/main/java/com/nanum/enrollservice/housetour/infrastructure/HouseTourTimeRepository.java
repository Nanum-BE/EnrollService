package com.nanum.enrollservice.housetour.infrastructure;

import com.nanum.enrollservice.housetour.domain.HouseTourTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseTourTimeRepository extends JpaRepository<HouseTourTime, Long> {
}
