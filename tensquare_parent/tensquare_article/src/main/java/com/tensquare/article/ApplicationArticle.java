package com.tensquare.article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.IdWorker;
import util.JwtUtils;

@SpringBootApplication
@EnableTransactionManagement
public class ApplicationArticle {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationArticle.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
	@Bean
	public JwtUtils getJwtUtils(){
		return new JwtUtils();
	}
}
