package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Feedback;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
/**
 * <p> 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("Feedback")
@RestController
@RequestMapping("/Feedback")
public class FeedbackController extends BaseController {


    @GetMapping("/queryFeedbackPage")
    @ApiOperation(value = "获取列表")
    public Map queryFeedbackPage(int status,int anonymity ,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(anonymity!=-1){
            queryMap.put("Anonymity", anonymity);
        }
        PageInfo<Feedback> page = this.ReadFeedbackService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setFeedbackStatus")
    @ApiOperation(value = "设置状态")
    public Map setFeedbackStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Feedback temp=JSON.parseObject(data,Feedback.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                FeedbackService.recoverByID(id);
            }else{
                FeedbackService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findFeedback/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findFeedback(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Feedback temp=ReadFeedbackService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        return returnMap;
    }


    @PostMapping("/modifyFeedback")
    @ApiOperation(value = "修改")
    public Map modifyFeedback(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Feedback temp = JSON.parseObject(data, Feedback.class);
        Feedback  obj=new Feedback();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadFeedbackService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setUserID(temp.getUserID());
        obj.setAnonymity(temp.getAnonymity());
        obj.setContent(temp.getContent());


        Feedback tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=FeedbackService.insert(obj);
        }else{
            tempObj=FeedbackService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
