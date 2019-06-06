package com.tensquare.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
id	string 标签ID
labelname	string 标签名称
state	string 状态
count	integer($int32)
recommend	string 是否推荐

}
 */
@Entity
@Table(name = "tb_label")
public class Label {
    @Id
    private String id;
    private String labelname;
    private String state;
    private Integer count;
    private String recommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
