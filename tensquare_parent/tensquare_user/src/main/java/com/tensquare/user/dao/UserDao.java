package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    //根据手机号查询用户
    User findByMobile(String mobile);

    ///增加粉丝数 当前数量加传过来的数量
    @Modifying
    @Query("update User set fanscount = fanscount + ?2 where id = ?1")
    void updateFanscount(String id,int fanscount);
    //增技术关注数
    @Modifying
    @Query("update User set followcount = followcount + ?2 where id = ?1")
    void updateFollowcount(String id,int followcount);
}
