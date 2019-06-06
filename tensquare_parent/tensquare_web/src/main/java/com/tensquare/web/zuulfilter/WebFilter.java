package com.tensquare.web.zuulfilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;


@Component
public class WebFilter extends ZuulFilter {
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

    //这个token是自定义的权限逻辑代码，原始的HTTP请求头不包括这个请求头对所以在网关转发时不会携带，必须获取添加到网关转发中
    @Override
    public Object run() throws ZuulException { //要执行的逻辑代码
        //获得原始请求头
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        //判断原始请求头是否包含 token
        String authorization = request.getHeader("Authorization");
        //请求头包含token 将token 添加到网关的转发请求中，让被调用的微服务进行权限验证
        if (!StringUtils.isEmpty(authorization)){ //不为空
            //添加到网关转发请求中 ， 不判断token是否正确交给后面的微服务拦截器进行验证
            requestContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }

}
