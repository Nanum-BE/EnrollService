package com.nanum.enrollservice.housetour.application;

import com.nanum.common.Role;
import com.nanum.enrollservice.housetour.dto.HouseTourDto;
import com.nanum.enrollservice.housetour.dto.HouseTourUpdateDto;
import com.nanum.enrollservice.housetour.vo.HouseTourResponse;
import com.nanum.enrollservice.housetour.vo.HouseTourTimeResponse;

import java.time.LocalDate;
import java.util.List;

public interface HouseTourService {
    void createHouseTour(HouseTourDto houseTourDto);
    List<HouseTourResponse> retrieveHouseTour(Long id, Role role);
    void updateHouseTour(HouseTourUpdateDto houseTourUpdateDto);
    List<HouseTourTimeResponse> retrieveTourTime(Long houseId, LocalDate date);
}
