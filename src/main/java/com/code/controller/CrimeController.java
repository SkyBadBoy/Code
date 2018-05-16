package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.config.rabbit.RabbitUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Crime;
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
@Api("Crime")
@RestController
@RequestMapping("/Crime")
public class CrimeController extends BaseController {


    @GetMapping("/queryCrimePage")
    @ApiOperation(value = "获取列表")
    public Map queryCrimePage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Crime> page = this.ReadCrimeService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Crime-queryCrimePage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setCrimeStatus")
    @ApiOperation(value = "设置状态")
    public Map setCrimeStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Crime temp=JSON.parseObject(data,Crime.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                CrimeService.recoverByID(id);
            }else{
                CrimeService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Crime-setCrimeStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findCrime/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findCrime(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Crime temp=ReadCrimeService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Crime-findCrime-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyCrime")
    @ApiOperation(value = "修改")
    public Map modifyCrime(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Crime temp = JSON.parseObject(data, Crime.class);
        Crime  obj=new Crime();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadCrimeService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }
        obj.setStatus(temp.getStatus());
        obj.setUserID(temp.getUserID());
        obj.setAdminID(temp.getAdminID());
        obj.setContent(temp.getContent());
        obj.setSrcType(temp.getSrcType());
        obj.setSrcID(temp.getSrcID());
        obj.setTime(temp.getTime());
        obj.setType(temp.getType());
        obj.setStartTime(temp.getStartTime());
        obj.setEndTime(temp.getEndTime());
        Crime tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=CrimeService.insert(obj);
        }else{
            tempObj=CrimeService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Crime-modifyCrime-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }



    @GetMapping("/cancelPunish")
    @ApiOperation(value = "取消处理")
    public Map cancelPunish(String id, HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Crime temp=ReadCrimeService.findById(id);
        if(temp!=null){
            temp.setType(3);
            temp=CrimeService.update(temp);
            if(temp!=null){
                returnMap.put("code",0);
                returnMap.put("message","处理成功");
            }else{
                returnMap.put("code",-1);
                returnMap.put("message","处理失败");
            }

        }else{
            returnMap.put("code",-1);
            returnMap.put("message","未查询到该条记录");
        }
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Crime-cancelPunish-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }
}
