package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope //标签为在BUS消息队列更新时默认只更新默认配置，现在自定义的也更新  标识该类也属于更新范围
@RestController
@RequestMapping("/label")
@CrossOrigin //也许跨域
public class LabelController {

    @Autowired
    private String zidingyi;

    @Autowired
    private LabelService labelService;


    //查询所有
    @RequestMapping(method = RequestMethod.GET)
     public Result fingAll(){
         List<Label> list = labelService.findAll();
        System.out.println(zidingyi);
         return new Result(true, StatusCode.OK,"查询成功",list);
     }
     //根据id查询
     @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findByid(@PathVariable String labelId){
         Label label = labelService.findbyId(labelId);
         return new Result(true,StatusCode.OK,"查询成功",label);
     }
     //添加
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.saveOrupdate(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    //修改
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.saveOrupdate(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    //删除
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String labelId){
        labelService.delete(labelId);
        return new Result(true,StatusCode.OK,"删除成功");
    }
    //条件查询
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }
    //条件加分页
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearchPage(@PathVariable Integer page,@PathVariable Integer size,@RequestBody Label label){
        Page<Label> searchPage = labelService.findSearchPage(page, size, label);                         //分页总记录数                页内容
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Label>(searchPage.getTotalElements(),searchPage.getContent()));
    }
}
