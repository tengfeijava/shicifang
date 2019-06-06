package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.tensquare.base.pojo.Label;
import util.IdWorker;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
   @Autowired
   private IdWorker idWorker;

    //增 或 改
    public void saveOrupdate(Label label){
        //判断对象里是否有id
        if (StringUtils.isEmpty(label.getId())){
          //没有id 生成id
            label.setId(idWorker.nextId()+"");//转为String类型
        }
        labelDao.save(label);//有id 为修改 没有就是添加\79
    }
    //根据id 查
    public Label findbyId(String labelID){
      return  labelDao.findById(labelID).get();
    }
    //查询全部
    public List<Label> findAll(){
      return    labelDao.findAll();
    }
    //删
    public void delete(String labelID){
        labelDao.deleteById(labelID);
    }
    //条件查询
    public List<Label> findSearch(Label label){
     /*   return  labelDao.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> plist=new ArrayList<>();

            if (!StringUtils.isEmpty(label.getId())){  //id 不为空
                Predicate p1 = criteriaBuilder.equal(root.get("id").as(String.class), label.getId());
                plist.add(p1);
            }
            if (label.getCount() != null){
                Predicate p2 = criteriaBuilder.equal(root.get("count").as(Integer.class), label.getCount());
                plist.add(p2);
            }
            if (!StringUtils.isEmpty(label.getLabelname())){
                Predicate p3 = criteriaBuilder.like(root.get("labelname").as(String.class), "%"+label.getLabelname()+"%");
                plist.add(p3);
            }
            if (!StringUtils.isEmpty(label.getRecommend())){
                Predicate p4 = criteriaBuilder.equal(root.get("recommend").as(String.class), label.getRecommend());
                plist.add(p4);
            }
            if (!StringUtils.isEmpty(label.getState())){
                Predicate p5 = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                plist.add(p5);
            }                            //转为数组              数组里 为 plist的长度
            return    criteriaBuilder.and(plist.toArray(new Predicate[plist.size()]));
        });*/
            //此参数可以指定 属性值为模糊查询  contains包含 模糊查询                               那个属性      函数式表达式只有一个参数是可以省略（）和大括号     contains包含
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("labelname",genericPropertyMatcher -> genericPropertyMatcher.contains());
         //Example.of 自动传入label 对象判断，根据对象里的条件查询
         return labelDao.findAll(Example.of(label,matcher));
    }
    //条件加分页
    public Page<Label> findSearchPage(Integer page,Integer size,Label label){
         ExampleMatcher matcher  = ExampleMatcher.matching().withMatcher("labelname",m -> m.contains());
                                                         //页数减一从零开始算，下一页    当前页个数
        return  labelDao.findAll(Example.of(label,matcher),PageRequest.of(page-1,size));

    }
}
