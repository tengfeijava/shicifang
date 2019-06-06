package com.tensquare.qa.feignclient;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"Base微服务调用失败，熔断器启动");
    }
}
