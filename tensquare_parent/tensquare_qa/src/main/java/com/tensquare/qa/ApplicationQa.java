package com.tensquare.qa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.IdWorker;
import util.JwtUtils;

@SpringBootApplication
@EnableFeignClients //此注解表示允许使用 Feign 远程调用 调用别的微服务系统方法
@EnableDiscoveryClient //发现微服务客户端注解
public class ApplicationQa {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationQa.class, args);
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
