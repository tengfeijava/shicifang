package com.tensquare.mongo;

import com.tensquare.spit.ApplicationSpit;
import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes= ApplicationSpit.class)
public class Demo {
    @Autowired
    private SpitService spitService;

    @Test
    //增加
    public void fun1() {
        Spit spit = new Spit();
        spit.setComment(0);
        spit.setContent("MongoDb好!");
        spit.setNickname("旭旭");
        spit.setPublishtime(new Date());
        spit.setShare(0);
        spit.setState("1");
        spit.setThumbup(0);
        spit.setUserid("2013");
        spit.setVisits(0);
        spit.setParentid("666");

        spitService.save(spit);

    }

    @Test
    //查询
    public void fun2() {

        Spit spit = spitService.findById("1088289867105964032");

        System.out.println(spit);
    }

    @Test
    //查询所有
    public void fun3() {

        List<Spit> spits = spitService.findAll();

        System.out.println(spits);
    }


    @Test
    //修改
    public void fun4() {

        Spit spit = spitService.findById("1088289867105964032");

        spit.setNickname("嘘嘘");

        spitService.update(spit);
    }

    @Test
    //删除
    public void fun5() {

        spitService.delete("1088289867105964032");

    }

    @Test
    //方法命名查询
    public void fun6() {

        //List<Spit> list = spitService.findByParentId("666");

       // System.out.println(list);
    }

    @Test
    //点赞1
    public void fun7() {

        Spit spit = spitService.findById("1088291780329345024");

        spit.setThumbup(spit.getThumbup()+1);

        spitService.update(spit);
    }
    @Autowired
    //mongo模板
    private MongoTemplate template;

    @Test
    //点赞2
    public void fun8() {
        Query query = new Query();
        //查询_id为1088291780329345024
        query.addCriteria(Criteria.where("_id").is("1088291780329345024"));
        Update update = new Update();
        update.inc("thumbup",1);
        template.updateFirst(query,update,"spit");

    }
}
