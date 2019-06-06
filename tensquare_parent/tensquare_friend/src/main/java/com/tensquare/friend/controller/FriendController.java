package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest httpServletRequest;//获取请求信息 （JWT验证）

    @Autowired
    private FriendService friendService;

    /**
     *
     * @param friendid 好友id
     * @param type 1:喜欢 ， 2:不喜欢
     * @return
     */
    @RequestMapping(value="/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result like(@PathVariable String friendid,@PathVariable String type){
      //获取登录用户id
        Claims claims = (Claims) httpServletRequest.getAttribute("claims_user");
        if (claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"请先登录");
        }

        if (type.equals("1")){ //喜欢
            friendService.addFriend(claims.getId(),friendid);
        }else {//不喜欢
            friendService.addNoFriend(claims.getId(),friendid);
        }
        return new Result(true,StatusCode.OK,"操作成功");
    }


    //删除好友
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFrienf(@PathVariable String friendid){
        //获取用户id
        Claims claims = (Claims) httpServletRequest.getAttribute("claims_user");
        if (claims == null){
            return new Result(false,StatusCode.ACCESSERROR,"请先登录");
        }
        friendService.deleteFriend(claims.getId(),friendid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
