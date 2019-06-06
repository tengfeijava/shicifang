package com.tensquare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.IdWorker;

@SpringBootApplication
@EnableTransactionManagement
public class ApplicationBase {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBase.class,args);
    }
    @Bean //生成id 数
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }
}
