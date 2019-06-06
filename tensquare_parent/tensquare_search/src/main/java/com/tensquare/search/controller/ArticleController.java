package com.tensquare.search.controller;


import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    //保存文章到索引库 Elasticsearch里
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.savr(article);
        return new Result(true, StatusCode.OK,"保存成功");
    }
    //从索引库搜索文章
    @RequestMapping(value = "/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findsarch(@PathVariable String keywords,Integer page,Integer size){
        Page<Article> search = articleService.findSearch(keywords, page, size);
        return new Result(true,StatusCode.OK,"搜索成功",new PageResult<Article>(search.getTotalElements(),search.getContent()));
    }
}
