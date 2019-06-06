package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{
    /**
     * 文章审核
     * 根据id 修改状态
     * @param articleId
     */
    @Modifying
    @Query(value = "update tb_article set state = ?2 where id = ?1",nativeQuery = true)
    void examine(String articleId,String state);


    /**
     * 文章点赞
     * @param articleId
     */
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup+1 where id = ?1",nativeQuery = true)
    void thumbup(String articleId);
}
