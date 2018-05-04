package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Access;
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
@Api("Access")
@RestController
@RequestMapping("/Access")
public class AccessController extends BaseController {


    @GetMapping("/queryAccessPage")
    @ApiOperation(value = "获取列表")
    public Map queryAccessPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Access> page = this.ReadAccessService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setAccessStatus")
    @ApiOperation(value = "设置状态")
    public Map setAccessStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Access temp=JSON.parseObject(data,Access.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                AccessService.recoverByID(id);
            }else{
                AccessService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findAccess/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findAccess(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Access temp=ReadAccessService.findById(id);
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


    @PostMapping("/modifyAccess")
    @ApiOperation(value = "修改")
    public Map modifyAccess(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Access temp = JSON.parseObject(data, Access.class);
        Access  obj=new Access();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadAccessService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setRequestURL(temp.getRequestURL());
        obj.setMethod(temp.getMethod());
        obj.setRemoteAddr(temp.getRemoteAddr());
        obj.setClassName(temp.getClassName());
        obj.setTime(temp.getTime());


        Access tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=AccessService.insert(obj);
        }else{
            tempObj=AccessService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
