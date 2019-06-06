package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.feignclient.UserClient;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    //点击喜欢
    public void addFriend(String userid,String friendid){
        System.err.println("String userid:"+userid);
        System.err.println("String friendid:"+friendid);
//        //查询是否喜欢过  如果喜欢过就抛出异常
//        if (friendDao.selectCount(userid,friendid)>0){
//            System.err.println("异常++++++++++++++++");
//            throw new RuntimeException("不要重复点击喜欢");
//        }
//
//        //没有喜欢过就创建Friend对象调Dao保存 islike字段 单项喜欢为0
//        friendDao.save(new Friend(userid,friendid,"0"));
//
//        //在判断两人是否都喜欢对方 改islike 字段为1
//        if(friendDao.selectCount(friendid,userid)>0){
//            friendDao.updateIslike(userid,friendid,"1");
//            friendDao.updateIslike(friendid,userid,"1");
//        }
//
//        //增加粉丝数
//        userClient.incFanscount(friendid,1);
//        System.err.println("粉丝数===============");
//        //增加关注
//        userClient.incFollowcount(userid,1);
//        System.err.println("关注数----------------");
//    }

        //1 查询Dao判断,之前是否已经喜欢过这个人
        System.err.println("111111111111111111");
        Friend friend = friendDao.selectCount(userid, friendid);
        System.err.println("Count/:"+friend);
        System.err.println("进入判断");
        if(StringUtils.isEmpty(friend)){    //为空代表第一次点击 否则为重复点击
            //2 创建Friend对象,调用Dao,存入数据库,表达喜欢
            System.err.println("222222222222222222222222");
            friendDao.save(new Friend(userid,friendid,"1"));
            //3 调用Dao,判断两人是否互相喜欢
            System.out.println("33333333333333333333333333333");
            Friend friend1 = friendDao.sleleFriendCount(friendid, userid);
            System.err.println("Count1:"+friend1);
            if(!StringUtils.isEmpty(friend1)){  //为空代表对方没有关注 不为空代表对方也关注了为双方都关注
                System.err.println("444444444444444444444");
                //互相喜欢 => 调用Dao,将双方的islike字段改为1
                friendDao.updateIslike(userid,friendid,"3");
                friendDao.updateIslike(friendid,userid,"3");
            }

            //维护关注数
            System.err.println("5555555555555555555555555");
            userClient.incFollowcount(userid,1);
            System.err.println("关注============");
            //维护粉丝数
            System.err.println("6666666666666666666666");
            userClient.incFanscount(friendid,1);
            System.err.println("粉丝------------");

        }else {
            // 喜欢过->抛出异常->提示已经点过喜欢
            System.err.println("异常+++++++++++++++++++++");
            throw new RuntimeException("不要重复点击喜欢");
        }

    }

    //不喜欢
    public void addNoFriend(String userid,String friendid){
        //向不喜欢表里添加一条记录
        noFriendDao.save(new NoFriend(userid,friendid));
    }

    //删除好友(在互相喜欢的情况下）
    public void deleteFriend(String userid,String friendid){
        //删除好友 删除friend表对应得记录
        friendDao.deleteFriend(userid,friendid);
        //向nofriend表添加一条记录
        addNoFriend(userid,friendid);
        //修改另一方字段改为单项喜欢 islike字段为 0
        friendDao.updateIslike(friendid,userid,"0");

        //增加粉丝数
        userClient.incFanscount(friendid,-1);
        //增加关注
        userClient.incFollowcount(userid,-1);
    }
}
