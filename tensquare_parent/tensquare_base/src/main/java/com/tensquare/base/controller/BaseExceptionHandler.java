package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常通知
@ControllerAdvice
public class BaseExceptionHandler {
    //出现什么样的异常
    @ExceptionHandler(value = Exception.class)
    @ResponseBody //响应
    public Result error(Exception e){
      e.printStackTrace();//打印到控制台

      return new Result(false, StatusCode.ERROR,"操作失败："+e.getMessage());
    }
}
