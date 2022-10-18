package com.nanum.enrollservice.client.vo;

import lombok.Data;

@Data
public class FeignResponse<T>{
    private T result;
}
