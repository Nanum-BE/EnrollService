package com.nanum.enrollservice.client;

import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.HostRoomResponse;
import com.nanum.enrollservice.client.vo.HouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "house-service")
public interface HouseServiceClient {

    @GetMapping(value = "/api/v1/houses/{houseId}/rooms/{roomId}", produces = "application/json")
    FeignResponse<HostRoomResponse> getHouseStatus(@PathVariable Long houseId, @PathVariable Long roomId);

    @GetMapping(value = "/api/v1/houses/house/{houseId}", produces = "application/json")
    FeignResponse<HouseResponse> getHouseDetails(@PathVariable Long houseId);
}
