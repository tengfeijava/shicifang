package com.tensquare.search.service;


import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    public void savr(Article article){
        articleDao.save(article);
    }
    //从索引库搜索文章  根据标题和内容搜索
    public Page<Article> findSearch(String keywords,Integer page,Integer size){
      return   articleDao.findByTitleLikeOrContentLike(keywords,keywords, PageRequest.of(page-1,size));
    }
}
