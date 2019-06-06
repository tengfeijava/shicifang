package com.tensquare.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="tb_friend")
@IdClass(Friend.class) //当表有多个主键时可以抽取到另一个类里，但是这里没必要所以写本类名代表抽取到自己类里
public class Friend implements Serializable {
    @Id
    private String friendid;
    @Id
    private String userid;
    private String islike;

    public Friend() {
    }

    public Friend(String friendid, String userid, String islike) {
        this.friendid = friendid;
        this.userid = userid;
        this.islike = islike;
    }

    public Friend(String friendid, String userid) {
        this.friendid = friendid;
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
