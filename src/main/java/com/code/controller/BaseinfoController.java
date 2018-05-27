package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.code.domain.Menu;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Baseinfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.code.config.rabbit.RabbitUtil;
/**
 * <p> 控制器 Class</p>
 *
 * @author majian 自动构建脚本
 */
@Api("Baseinfo")
@RestController
@RequestMapping("/Baseinfo")
public class BaseinfoController extends BaseController {


    @GetMapping("/queryBaseinfoPage")
    @ApiOperation(value = "获取列表")
    public Map queryBaseinfoPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Baseinfo> page = this.ReadBaseinfoService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Baseinfo-queryBaseinfoPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/queryBaseinfoAll")
    @ApiOperation(value = "获取所有的目录列表")
    public Map queryBaseinfoAll(HttpServletRequest request,String parentID) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        queryMap.put(Baseinfo.COLUMN_ParentID, parentID);
        PageInfo<Baseinfo> page = this.ReadBaseinfoService.queryPage(queryMap, 0, 0);
        returnMap.put("data",page.getList());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Baseinfo-queryBaseinfoAll】目录列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setBaseinfoStatus")
    @ApiOperation(value = "设置状态")
    public Map setBaseinfoStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Baseinfo temp=JSON.parseObject(data,Baseinfo.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                BaseinfoService.recoverByID(id);
            }else{
                BaseinfoService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Baseinfo-setBaseinfoStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findBaseinfo/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findBaseinfo(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Baseinfo temp=ReadBaseinfoService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Baseinfo-findBaseinfo-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyBaseinfo")
    @ApiOperation(value = "修改")
    public Map modifyBaseinfo(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Baseinfo temp = JSON.parseObject(data, Baseinfo.class);
        Baseinfo  obj=new Baseinfo();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadBaseinfoService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setName(temp.getName());
        obj.setMemo(temp.getMemo());
        obj.setParentID(temp.getParentID());


        Baseinfo tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=BaseinfoService.insert(obj);
        }else{
            tempObj=BaseinfoService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Baseinfo-modifyBaseinfo-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
