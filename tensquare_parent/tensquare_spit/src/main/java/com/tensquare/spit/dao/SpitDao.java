package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpitDao extends MongoRepository<Spit,String> {

    //方法命名查询 => 根据parentId查询
   Page<Spit> findByParentid(String parentid, Pageable pageable);
}
