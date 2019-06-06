package com.tensquare.qa.pojo;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

//label 和 problem 多对多关系的中间表
@Entity
@Table(name = "tb_pl")
public class Pl implements Serializable {
    @Id
    private String problemid;//问题
    @Id
    private String labelid;//标签

    public String getProblemid() {
        return problemid;
    }

    public void setProblemid(String problemid) {
        this.problemid = problemid;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }
}
