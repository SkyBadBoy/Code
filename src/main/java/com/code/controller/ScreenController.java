package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Screen;
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
@Api("Screen")
@RestController
@RequestMapping("/Screen")
public class ScreenController extends BaseController {


    @GetMapping("/queryScreenPage")
    @ApiOperation(value = "获取列表")
    public Map queryScreenPage(int status,int type,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(type!=-1){
            queryMap.put("Type", type);
        }
        PageInfo<Screen> page = this.ReadScreenService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setScreenStatus")
    @ApiOperation(value = "设置状态")
    public Map setScreenStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Screen temp=JSON.parseObject(data,Screen.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ScreenService.recoverByID(id);
            }else{
                ScreenService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findScreen/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findScreen(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Screen temp=ReadScreenService.findById(id);
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


    @PostMapping("/modifyScreen")
    @ApiOperation(value = "修改")
    public Map modifyScreen(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Screen temp = JSON.parseObject(data, Screen.class);
        Screen  obj=new Screen();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadScreenService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setTitle(temp.getTitle());
        obj.setUrl(temp.getUrl());
        obj.setType(temp.getType());
        obj.setStartTime(temp.getStartTime());
        obj.setEndTime(temp.getEndTime());
        obj.setModifyTime(temp.getModifyTime());
        obj.setAdminID(temp.getAdminID());
        obj.setClickUrl(temp.getClickUrl());


        Screen tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=ScreenService.insert(obj);
        }else{
            tempObj=ScreenService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
