package com.tensquare.qa.feignclient;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//指定该接口用于配置远程调用，并指定远程服务名称->微服务名称不可包含下划线
// 注解为调用标签base模块 填写base微服务名称
@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)//熔断器
public interface LabelClient {


    //  根据 Id 查询 Label 标签
     //                     路径补全  @PathVariable("labelId") 在 Feign 里必须指定不可以忽略
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
