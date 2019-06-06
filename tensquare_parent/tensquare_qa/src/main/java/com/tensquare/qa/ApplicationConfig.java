package com.tensquare.qa;


import com.tensquare.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration //配置文件注解
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;//拦截器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor)//添加拦截器
                .addPathPatterns("/**")//拦截是所有路径
                .excludePathPatterns("/**/login");//不拦截登录路径
    }
}
