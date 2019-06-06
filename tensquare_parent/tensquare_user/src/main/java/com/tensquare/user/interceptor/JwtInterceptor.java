package com.tensquare.user.interceptor;

import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;

@Component  // 交给Spring管理
                                     //继承SpringMVC 拦截器
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //校验当前用户登录并且是管理员才可以删除用户
        String authorization = request.getHeader("Authorization");
        //请求头内容不为空表示登录             或者 头内容是以Bearer加空格开头代表成功
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")){

            //提取头信息
            String token =authorization.substring(7);//截取从第八位开始的字符串，实际就是去掉了Bearer加空格七位

            Claims claims = jwtUtils.parserJWT(token);

            //如果token 不为空  或者   是管理员
            if (claims != null && "admin".equals(claims.get("roles"))){
                //向请求中添加载荷信息
                request.setAttribute("claima_admin",claims);
            }
           if (claims != null && "user".equals(claims.get("roles"))){
               request.setAttribute("claima_user",claims);
           }

        }


        return true;
    }
}
