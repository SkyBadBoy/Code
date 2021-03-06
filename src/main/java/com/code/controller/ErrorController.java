package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.config.rabbit.RabbitUtil;
import com.code.domain.Return;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Error;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
/**
 * <p> 控制器 Class</p>
 *
 *
 * 错误:-1    正常:0    token:1    地址访问受限:2
 *
 * @author majian 自动构建脚本
 */
@Api("Error")
@RestController
@RequestMapping("/Error")
public class ErrorController extends BaseController {


    @GetMapping("")
    @ApiOperation(value = "重定向的错误")
    public Map error(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("code", -1);
        returnMap.put("message", "服务器发送错误,请稍后重试");
        return returnMap;
    }
    @GetMapping("errorToken")
    @ApiOperation(value = "重定向Token的错误")
    public Return ErrorErrorToken() {
        Return returnMap = null;
        returnMap=CommonUntil.ReturnMap(1,"Token异常,请重新登录",null);
        return returnMap;
    }

    @GetMapping("errorBlack")
    @ApiOperation(value = "地址访问受限")
    public Return errorBlack() {
        Return returnMap = null;
        returnMap=CommonUntil.ReturnMap(2,"访问受限,请联系管理人员",null);
        return returnMap;
    }

    @GetMapping("/queryErrorPage")
    @ApiOperation(value = "获取列表")
    public Map queryErrorPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Error> page = this.ReadErrorService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Error-queryErrorPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setErrorStatus")
    @ApiOperation(value = "设置状态")
    public Map setErrorStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Error temp=JSON.parseObject(data,Error.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ErrorService.recoverByID(id);
            }else{
                ErrorService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Error-setErrorStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findError/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findError(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Error temp=ReadErrorService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Error-findError-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyError")
    @ApiOperation(value = "修改")
    public Map modifyError(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Error temp = JSON.parseObject(data, Error.class);
        Error  obj=new Error();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadErrorService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setUserID(temp.getUserID());
        obj.setName(temp.getName());
        obj.setMessage(temp.getMessage());
        obj.setClassName(temp.getClassName());


        Error tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=ErrorService.insert(obj);
        }else{
            tempObj=ErrorService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Error-modifyError-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }



}
