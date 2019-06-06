package com.tensquare.article.controller;


import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //新增评论
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Comment comment){
        commentService.save(comment);
        return new Result(true, StatusCode.OK,"新增成功");
    }
    //根据文章 Id 查询评论
     @RequestMapping(value = "/{articleid}",method = RequestMethod.POST)
    public Result findById(@PathVariable String articleid){
         List<Comment> commentList = commentService.findById(articleid);
         return new Result(true,StatusCode.OK,"查询成功",commentList);
     }
    //根据评论 Id 删除评论
    @RequestMapping(value = "/{commentid}",method = RequestMethod.DELETE)
    public Result deleteByid(@PathVariable String commentid){
        commentService.deleteByid(commentid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
