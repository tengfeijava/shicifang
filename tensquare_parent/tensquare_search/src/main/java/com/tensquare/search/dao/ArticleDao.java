package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleDao extends ElasticsearchRepository<Article,String> {
    //从索引库搜索文章  根据标题和内容搜索
    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable of);


}
