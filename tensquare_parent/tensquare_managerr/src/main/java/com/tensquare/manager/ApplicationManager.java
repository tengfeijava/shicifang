package com.tensquare.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtils;

//启动报错：分析:由于在新建项目的时候我添加了mysql组件,但是我将数据源注释掉了，也就是没配数据源。
                      //排斥数据源
@SpringBootApplication
@EnableZuulProxy //开启网关
public class ApplicationManager {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationManager.class,args);
    }

    @Bean
    public JwtUtils getJwtUtils(){
        return new JwtUtils();
    }
}
