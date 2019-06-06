package com.tensquare.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;
import util.JwtUtils;

@SpringBootApplication
public class ApplicationSpit {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSpit.class, args);
    }
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

    @Bean
    public JwtUtils getJwtUtils(){
        return new JwtUtils();
    }
}
