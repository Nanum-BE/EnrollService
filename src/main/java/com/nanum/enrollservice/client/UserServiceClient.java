package com.nanum.enrollservice.client;

import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping(value = "/api/v1/users/{userId}", produces = "application/json")
    FeignResponse<UserResponse> getUser(@PathVariable Long userId);
}
