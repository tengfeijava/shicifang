package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    //增
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
         //获取请求头属性 授权的登录用户
        Claims claims_user = (Claims) httpServletRequest.getAttribute("claims_user");
         //是空代表没有授权
        if (claims_user == null){
            return new Result(false,StatusCode.ACCESSERROR,"未登录请先登录");
        }
        //如果登录从请求属性里获取用户ID
        String id = claims_user.getId();
        //设置成当前操作用户id
        spit.setUserid(id);
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"保存成功");
    }
    //删
    @RequestMapping(value ="/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.delete(spitId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    //改
    @RequestMapping(value="/{spitId}",method = RequestMethod.POST)
    public Result update(@PathVariable String spitId,@RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    //查所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> all = spitService.findAll();
        return new Result(true,StatusCode.OK,"查询成功",all);
    }
    //查
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result select(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);
        return new Result(true,StatusCode.OK,"查询成功",spit);
    }
    //根据父 id 查询
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result selectid(@PathVariable String parentid,@PathVariable Integer page,@PathVariable Integer size){
        Page<Spit> page1 = spitService.findByParentId(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(page1.getTotalElements(),page1.getContent()));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    //根据 id 对吐槽点赞
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.POST)
    public Result thumbup(@PathVariable String spitId){
        String userId = "11111";//初始化用户Id

        //获取存入的用户id 及吐槽 id
        String flag = (String) redisTemplate.opsForValue().get("spit_" + userId + "_" + spitId);

        //不为空表示点过赞
        if (!StringUtils.isEmpty(flag)){
            return new Result(false,StatusCode.REPERROR,"不可重复点赞");
        }else {
            //为点过赞
            spitService.thumbup(spitId);
            redisTemplate.opsForValue().set("spit_"+userId +"_"+spitId,"1");
            return new Result(true,StatusCode.OK,"点赞成功");
        }
    }
}
