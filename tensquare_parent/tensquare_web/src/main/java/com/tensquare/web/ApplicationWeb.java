package com.tensquare.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;



@SpringBootApplication
@EnableZuulProxy //开启网关
public class ApplicationWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class,args);
    }


}
