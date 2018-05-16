package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.domain.Return;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Role;
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
@Api("Role")
@RestController
@RequestMapping("/Role")
public class RoleController extends BaseController {


    @GetMapping("/queryRolePage")
    @ApiOperation(value = "获取列表")
    public Map queryRolePage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Role> page = this.ReadRoleService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @GetMapping("/queryRolePageAll")
    @ApiOperation(value = "获取列表")
    public Return<Role> queryRolePageAll(HttpServletRequest request) {
        Return returnMap = null;
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        List<Role> page = this.ReadRoleService.query(queryMap);
        returnMap=CommonUntil.ReturnMap(0,"数据获取成功",page);
        return returnMap;
    }

    @PostMapping("/setRoleStatus")
    @ApiOperation(value = "设置状态")
    public Map setRoleStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Role temp=JSON.parseObject(data,Role.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                RoleService.recoverByID(id);
            }else{
                RoleService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findRole/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findRole(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Role temp=ReadRoleService.findById(id);
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


    @PostMapping("/modifyRole")
    @ApiOperation(value = "修改")
    public Map modifyRole(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Role temp = JSON.parseObject(data, Role.class);
        Role  obj=new Role();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadRoleService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }
        obj.setName(temp.getName());
        obj.setAdminID("0");
        Role tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=RoleService.insert(obj);
        }else{
            tempObj=RoleService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
