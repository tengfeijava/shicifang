package com.tensquare.gathering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.IdWorker;
@SpringBootApplication
@EnableTransactionManagement
public class ApplicationGathering {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationGathering.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	
}
