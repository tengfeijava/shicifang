package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private IdWorker idWorker;

    //新增评论
    public void save(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        commentDao.save(comment);
    }
    //根据文章 Id 查询评论
    public List<Comment> findById(String articleid){
        return commentDao.findByArticleid(articleid);
    }
    //根据评论 Id 删除评论
    public void deleteByid(String commentId){
        commentDao.deleteById(commentId);
    }
}
