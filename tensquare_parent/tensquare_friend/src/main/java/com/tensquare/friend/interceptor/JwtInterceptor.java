package com.tensquare.friend.interceptor;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component  //交给Speing管理    继承   拦截器
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtils jwtUtils; //JWT规范
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头
        String authorization = request.getHeader("Authorization");
        //不为空                或者    事以 Bearer加空格开头的
        if (!StringUtils.isEmpty(authorization) && authorization.startsWith("Bearer ")){

            String tonke = authorization.substring(7);

            Claims claims = jwtUtils.parserJWT(tonke);
             //  不为空        或者    是管理员      获取claims里面的授权
            if (claims != null && "admin".equals(claims.get("roles"))){
                request.setAttribute("claims_admin",claims);//向载荷中添加解析正确的信息
            }
            if (claims != null && "user".equals(claims.get("roles"))){
                request.setAttribute("claims_user",claims);
            }
        }
        return true;
    }
}
