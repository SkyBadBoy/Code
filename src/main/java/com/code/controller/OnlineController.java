package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.config.rabbit.RabbitUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Online;
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
@Api("Online")
@RestController
@RequestMapping("/Online")
public class OnlineController extends BaseController {


    @GetMapping("/queryOnlinePage")
    @ApiOperation(value = "获取列表")
    public Map queryOnlinePage(int status,String search,int type,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        queryMap.put("Type", type);
        PageInfo<Online> page = this.ReadOnlineService.queryPage(queryMap, pageNumber, pageSize);
        for (Online o:page.getList()) {
            if(type==0){
                o.setUser(ReadUserService.findById(o.getUserID()));
            }else{
                o.setAdmin(ReadAdminService.findById(o.getUserID()));
            }
        }
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Online-queryOnlinePage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setOnlineStatus")
    @ApiOperation(value = "设置状态")
    public Map setOnlineStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Online temp=JSON.parseObject(data,Online.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                OnlineService.recoverByID(id);
            }else{
                OnlineService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Online-setOnlineStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findOnline/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findOnline(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Online temp=ReadOnlineService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Online-findOnline-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyOnline")
    @ApiOperation(value = "修改")
    public Map modifyOnline(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Online temp = JSON.parseObject(data, Online.class);
        Online  obj=new Online();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadOnlineService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setUserID(temp.getUserID());
        obj.setSession(temp.getSession());
        obj.setModifyTime(temp.getModifyTime());
        obj.setVersion(temp.getVersion());
        obj.setType(temp.getType());


        Online tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=OnlineService.insert(obj);
        }else{
            tempObj=OnlineService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Online-modifyOnline-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
