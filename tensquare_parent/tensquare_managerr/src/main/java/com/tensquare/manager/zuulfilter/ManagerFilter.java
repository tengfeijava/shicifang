package com.tensquare.manager.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtils;


import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Override
    public String filterType() {  //   指定zuul过滤器的过滤方式
//        pre ：可以在请求被路由之前调用
//        route ：在路由请求时候被调
//        post ：在route和error过滤器之后被调用
//        error ：处理请求时发生错误时被调用
        return "pre";
    }

    @Override
    public int filterOrder() {  //指定过滤器的执行优先级
        //数字越小越先执行   ，当前只有一个过滤器所以直接返回 0 最先执行
        return 0;
    }

    @Override
    public boolean shouldFilter() {  //是否启用该过滤器
        return true;
    }

    @Autowired
    private JwtUtils jwtUtils;

    //这个token是自定义的权限逻辑代码，原始的HTTP请求头不包括这个请求头对所以在网关转发时不会携带，必须获取添加到网关转发中
    @Override
    public Object run() throws ZuulException { //要执行的逻辑代码
        RequestContext requestContext= RequestContext.getCurrentContext();
        //获得原始请求对象
        HttpServletRequest request = requestContext.getRequest();

        //网关工作时,使用的请求方式是OPTIONS,直接放行OPTIONS请求
        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        //提取当前访问路径
        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login")>0){
            //如果访问的是登录页面就放行
            System.out.println("登录页面"+url);
            return null;
        }
        //获得token
        String authorization = request.getHeader("Authorization");
        //判断不为空  或者是以Bearer 加空格开头的
        if (authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            //解析字符串
            Claims claims = jwtUtils.parserJWT(token);
            if (claims != null){
                //判断当前用户是否为admin 管理员
                if ("admin".equals(claims.get("roles"))){
                    //将token继续添加到网关转发的请求中
                    requestContext.addZuulRequestHeader("Authorization",authorization);
                    //放行
                    return null;
                }
            }
        }
        //没有token|有token但是提取失败|角色不是admin  => 终止网关转发请求
        //终止网关转发请求
        requestContext.setSendZuulResponse(false);
        //执行状态响应码
        requestContext.setResponseStatusCode(401);
        //执定响应体类型和编码
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        //指定响应体内容
        requestContext.setResponseBody("不是管理员无权访问");
        return null;
    }
}
