package com.nanum.enrollservice.client;

import com.nanum.enrollservice.client.vo.FeignResponse;
import com.nanum.enrollservice.client.vo.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping(value = "/api/v1/users/{userId}", produces = "application/json")
    FeignResponse<UserResponse> getUser(@PathVariable Long userId);

    @PostMapping(value = "/api/v1/sms/sends/tour/approve/{userId}", produces = "application/json")
    void sendTourApproveSMS(@PathVariable Long userId);

    @PostMapping(value = "/api/v1/sms/sends/tour/reject/{userId}", produces = "application/json")
    void sendTourRejectSMS(@PathVariable Long userId);

    @PostMapping(value = "api/v1/sms/sends/tour/complete/{userId}", produces = "application/json")
    void sendTourCompleteSMS(@PathVariable Long userId);

    @PostMapping(value = "api/v1/sms/sends/move-in/approve/{userId}", produces = "application/json")
    void sendMoveInApproveSMS(@PathVariable Long userId);

    @PostMapping(value = "api/v1/sms/sends/move-in/reject/{userId}", produces = "application/json")
    void sendMoveInRejectSMS(@PathVariable Long userId);

    @PostMapping(value = "api/v1/sms/sends/move-in/complete/{userId}", produces = "application/json")
    void sendMoveInCompleteSMS(@PathVariable Long userId);
}
