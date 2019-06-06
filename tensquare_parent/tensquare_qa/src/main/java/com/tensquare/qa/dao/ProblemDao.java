package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{
    /**
     最新问答列表
     需求分析：最新回复的问题显示在上方， 按回复时间降序排序
     */
    @Query("select p from Problem p where p.id in ( select pl.problemid from Pl pl where pl.labelid = ?1 ) order by p.replytime Desc ")
                                                // 分页
    Page<Problem> findNewList(String labelid, Pageable pageable);
    /*
       热门回答列表
       需求分析：按回复数降序
       */
   @Query("select p from Problem p where p.id in ( select pl.problemid from Pl pl where pl.labelid = ?1) order by p.reply Desc ")
    Page<Problem> findhotlist(String labelid, Pageable pageable);

    /*
       等待回答列表
       需求分析：根据回复数按时间降序
       */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1) and p.reply = 0 order by p.createtime Desc ")
    Page<Problem> waitList(String labelid, Pageable pageable);


}
