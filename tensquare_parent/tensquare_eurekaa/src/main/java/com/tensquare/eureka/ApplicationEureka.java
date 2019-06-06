package com.tensquare.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer //此注解是 开启 EurekaServer 服务
public class ApplicationEureka {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka.class,args);
    }
}
