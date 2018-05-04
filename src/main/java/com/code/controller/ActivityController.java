package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Activity;
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
@Api("Activity")
@RestController
@RequestMapping("/Activity")
public class ActivityController extends BaseController {


    @GetMapping("/queryActivityPage")
    @ApiOperation(value = "获取列表")
    public Map queryActivityPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Activity> page = this.ReadActivityService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setActivityStatus")
    @ApiOperation(value = "设置状态")
    public Map setActivityStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Activity temp=JSON.parseObject(data,Activity.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ActivityService.recoverByID(id);
            }else{
                ActivityService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findActivity/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findActivity(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Activity temp=ReadActivityService.findById(id);
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


    @PostMapping("/modifyActivity")
    @ApiOperation(value = "修改")
    public Map modifyActivity(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Activity temp = JSON.parseObject(data, Activity.class);
        Activity  obj=new Activity();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadActivityService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setTitle(temp.getTitle());
        obj.setContent(temp.getContent());
        obj.setImageID(temp.getImageID());
        obj.setModifyTime(temp.getModifyTime());
        obj.setHot(temp.getHot());
        obj.setWeight(temp.getWeight());
        obj.setWeChatID(temp.getWeChatID());
        obj.setPraise(temp.getPraise());
        obj.setEndTime(temp.getEndTime());
        obj.setSponsor(temp.getSponsor());
        obj.setSBrief(temp.getSBrief());
        obj.setSImage(temp.getSImage());
        obj.setApplyCount(temp.getApplyCount());
        obj.setApplyLimit(temp.getApplyLimit());
        obj.setApplyMoney(temp.getApplyMoney());
        obj.setPayType(temp.getPayType());


        Activity tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=ActivityService.insert(obj);
        }else{
            tempObj=ActivityService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
