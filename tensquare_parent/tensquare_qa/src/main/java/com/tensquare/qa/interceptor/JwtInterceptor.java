package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component  // 交给Spring管理        //继承SpringMVC 拦截器
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求头
        String authorization = request.getHeader("Authorization");
         //不为空                或者    事以 Bearer加空格开头的
        if (authorization != null && authorization.startsWith("Bearer ")){

            String token = authorization.substring(7); //截取字符串，获得token

            Claims claims = jwtUtils.parserJWT(token);//解析token
            //   不为空       或者       是管理员
            if (claims != null && "admin".equals(claims.get("roles"))){
                request.setAttribute("admin_claims",claims);//向载荷中添加信息
            }
            if (claims != null && "user".equals(claims.get("roles"))){
                request.setAttribute("user_claims",claims);
            }
        }
        return true;
    }
}