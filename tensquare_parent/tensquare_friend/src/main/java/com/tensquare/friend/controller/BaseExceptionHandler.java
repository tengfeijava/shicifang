package com.tensquare.friend.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//配置异常处理通知(通知类型:异常通知)
@ControllerAdvice
public class BaseExceptionHandler {
    //指定出现什么异常时,执行该通知代码
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        //打印异常到控制台(方便调试)
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "操作失败:"+e.getMessage());
    }
}

