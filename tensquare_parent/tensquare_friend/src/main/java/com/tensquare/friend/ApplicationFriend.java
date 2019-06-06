package com.tensquare.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import util.JwtUtils;

@SpringBootApplication
@EnableFeignClients //允许远程调用
@EnableDiscoveryClient //发现客户端
public class ApplicationFriend {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationFriend.class,args);
    }
    @Bean
    public JwtUtils getJwtUtils(){
        return new JwtUtils();
    }
}
