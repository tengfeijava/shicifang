package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import util.IdWorker;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;

    //增
    public void save(Spit spit){
        spit.set_id(idWorker.nextId()+"");

        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //吐槽 id 不为空
        if (!StringUtils.isEmpty(spit.getParentid())){
            //为父级吐槽增加回复数
            //根据 Parentid 查询出当前父级吐槽
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            //在指定的父级吐槽id上 回复内容加一
            Update update = new Update();
            update.inc("comment",1);
            //更新
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }
    //删
    public void delete(String _id){
        spitDao.deleteById(_id);
    }
    //改
    public  void update(Spit spit){
        spitDao.save(spit);
    }
    //查 根据id
    public Spit findById(String _id){
        return spitDao.findById(_id).get();
    }
    //查 查询所有
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    //查 根据父级吐槽id查询
    public Page<Spit> findByParentId(String parentId,Integer page,Integer size){
        return spitDao.findByParentid(parentId, PageRequest.of(page-1,size));
    }


    public void thumbup(String parentid) {
        //根据id创建查询条件
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(parentid));
        //指定要更新的内容(在原有值上加1)
        Update update = new Update();
        update.inc("thumbup",1);
        //更新
         mongoTemplate.updateFirst(query,update,"spit");
    }
}
