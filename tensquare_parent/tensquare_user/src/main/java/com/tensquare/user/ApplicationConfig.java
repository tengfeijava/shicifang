package com.tensquare.user;

import com.tensquare.user.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;



@Configuration // SpringMVC 是配置文件 SpringBoot 使用@Configuration注解配置
                                       //继承配置文件接口 启动拦截器
public class ApplicationConfig extends WebMvcConfigurationSupport {

    @Autowired  //注入拦截器
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
         //注册拦截器 相当于SpringMVC 的 xml 文件
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") //拦截路径  所有路径
                .excludePathPatterns("/**/login");//不拦截路径   登录路径不拦截所有已login结尾的不拦截
    }
}
