package com.tensquare.friend.dao;


import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    //根据用户id 和 朋友id 查询是否喜欢过   查询结果大于0 代表喜欢过这个朋友
    @Query("select userid,friendid,islike from Friend  where userid = ?1 and friendid = ?2 ")
    Friend selectCount(String userid,String friendid);

    @Query("select userid,friendid,islike from Friend  where friendid = ?1 and userid = ?2")
    Friend sleleFriendCount(String friendid,String userid);
    //根据用户id 和 朋友id 查询 互相喜欢修改 islike 字段为1
    @Modifying
    @Query("update Friend set islike = ?3 where userid = ?1 and friendid = ?2")
    void updateIslike(String userid,String friendid,String islike);

    //删除好友
    @Modifying
    @Query("delete from Friend where userid = ?1 and friendid = ?2")
    void deleteFriend(String userid,String friendid);
}
