package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.code.domain.Return;
import com.code.domain.Role;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Department;
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
@Api("Department")
@RestController
@RequestMapping("/Department")
public class DepartmentController extends BaseController {


    @GetMapping("/queryDepartmentPage")
    @ApiOperation(value = "获取列表")
    public Map queryDepartmentPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Department> page = this.ReadDepartmentService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Department-queryDepartmentPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/queryDepartmentPageAll")
    @ApiOperation(value = "获取列表")
    public Return<Role> queryDepartmentPageAll(HttpServletRequest request) {
        Return returnMap = null;
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        List<Department> page = this.ReadDepartmentService.query(queryMap);
        returnMap=CommonUntil.ReturnMap(0,"数据获取成功",page);
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Department-queryDepartmentPageAll】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/setDepartmentStatus")
    @ApiOperation(value = "设置状态")
    public Map setDepartmentStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Department temp=JSON.parseObject(data,Department.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                DepartmentService.recoverByID(id);
            }else{
                DepartmentService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Department-setDepartmentStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findDepartment/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findDepartment(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Department temp=ReadDepartmentService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Department-findDepartment-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyDepartment")
    @ApiOperation(value = "修改")
    public Map modifyDepartment(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Department temp = JSON.parseObject(data, Department.class);
        Department  obj=new Department();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadDepartmentService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setName(temp.getName());
        obj.setMemo(temp.getMemo());
        obj.setParentID(temp.getParentID());


        Department tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=DepartmentService.insert(obj);
        }else{
            tempObj=DepartmentService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Department-modifyDepartment-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
