package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

   @Autowired
   private JwtUtils jwtUtils;

	//用原声请求获取请求头
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
//		//校验当前用户登录并且是管理员才可以删除用户
//        String authorization = request.getHeader("Authorization");
//        //请求头内容为空表示未登录             并且 头内容不是以Bearer加空格开头代表失败
//        if (StringUtils.isEmpty(authorization) || !authorization.startsWith("Bearer ")){
//            return new Result(false,StatusCode.ACCESSERROR,"未登录或没有权限");
//        }
//        //提取头信息
//        String token =authorization.substring(7);//截取从第八位开始的字符串，实际就是去掉了Bearer加空格七位
//
//        Claims claims = jwtUtils.parserJWT(token);
//        //如果token 为空   并且   不是管理员
//        if (claims == null || !"admin".equals(claims.get("roles"))){
//            return new Result(false,StatusCode.ACCESSERROR,"未登录或没有权限");
//        }

		//从拦截器中获取 请求 必须是管理员才能删除
        Object claima = request.getAttribute("claima_admin");

        if (claima == null){
            return new Result(false,StatusCode.ACCESSERROR,"未登录或没有权限");
        }
        userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 *  发短信
	 */
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile){
		userService.smsMQ(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	/**
	 *  用户注册 并校验验证码
	 */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result register(@RequestBody User user,@PathVariable String code){
		userService.register(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}
	/**
	 *  用户登录 根据手机号和密码查询
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		User userlogin= userService.login(user.getMobile(),user.getPassword());

		if (userlogin != null){
             //授权
            String jwt = jwtUtils.createJWT(userlogin.getId(), userlogin.getNickname(), "user");

            Map<String,String> map =new  HashMap();
            map.put("token",jwt); //授权类型，载荷，签名
            map.put("name",userlogin.getNickname());//名字
            map.put("avatar",userlogin.getAvatar());// 普通用户头像

            return new Result(true,StatusCode.OK,"登录成功",map);
		}else {
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
	}

	/**  远程调用
	 * 增加粉丝数
	 * @param userid  用户id
	 * @param x 粉丝数量
	 */
	@RequestMapping(value = "/incfans/{userid}/{x}",method = RequestMethod.POST)
	public void incFanscount(@PathVariable String userid,@PathVariable int x){
		userService.updateFanscount(userid,x);
	}

	/**  远程调用
	 *  增加关注数
	 * @param userid 用户id
 	 * @param x 关注数量
	 */
	@RequestMapping(value = "/incfollow/{userid}/{x}",method = RequestMethod.POST)
	public void incFollowcount(@PathVariable String userid,@PathVariable int x){
		userService.updateFollowcount(userid,x);
	}
}
