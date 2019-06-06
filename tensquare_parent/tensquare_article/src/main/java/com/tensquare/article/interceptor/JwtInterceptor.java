package com.tensquare.article.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头
        String authorization = request.getHeader("Authorization");
          // 请求头不等于空      或者   是以  Bearer加空格开头的
        if (authorization != null && authorization.startsWith("Bearer ")){
            //截取Bearer加空格开头 以后1的字符
            String token = authorization.substring(7);
            //解析字符串
            Claims claims = jwtUtils.parserJWT(token);
            //不为空          并且  授权
            if (claims != null && "admin".equals(claims.get("roles"))){
                //设置到请求属性里
                request.setAttribute("claims_admin",claims);
            }
            if (claims != null && "user".equals("roles")){
                request.setAttribute("claims_user",claims);
            }
        }
        return true;
    }
}
