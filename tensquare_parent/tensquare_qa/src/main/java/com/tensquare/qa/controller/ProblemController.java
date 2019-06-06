package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import com.tensquare.qa.feignclient.LabelClient;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest httpServletRequest;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		//用户必须登录才可以发布问题
        Claims claims_user = (Claims) httpServletRequest.getAttribute("claims_user");

        if (claims_user == null){//等于空未登录
            return new Result(false,StatusCode.ACCESSERROR,"未登录请先登录");
        }
        //一登录获得当前用户id
        String userId = claims_user.getId();
        //将问题发布人 Id 设置为当前登录用户 id
        problem.setUserid(userId);

        //增加问题
        problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	/**
	 最新问答列表
	 需求分析：最新回复的问题显示在上方， 按回复时间降序排序
	 */
	@RequestMapping(value="/newlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result newList(@PathVariable String  label , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findNewList(label,page,size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}
	/*
	 热门回答列表
	 需求分析：按回复数降序
	 */
	@RequestMapping(value = "/hotlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result hotlist(@PathVariable String label,@PathVariable int page,@PathVariable int size){
        Page<Problem> pagelist = problemService.findhotlist(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pagelist.getTotalElements(),pagelist.getContent()));
	}
	/*
          等待回答列表
          需求分析：根据回复数按时间降序
          */
	@RequestMapping(value = "/waitlist/{label}/{page}/{size}",method = RequestMethod.GET)
	public Result waitList(@PathVariable String label,@PathVariable int page,@PathVariable int size){
		Page<Problem> pagelis = problemService.waitList(label,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(pagelis.getTotalElements(),pagelis.getContent()));
	}

    //接口远程调用
	@Autowired
	private LabelClient labelClient;


    //根据Id 查询 Lable (远程调用）
	@RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
	public Result findLableByid(@PathVariable String id){
		return labelClient.findById(id);
	}
}

