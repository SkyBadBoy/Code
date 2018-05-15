package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.User;
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
@Api("User")
@RestController
@RequestMapping("/User")
public class UserController extends BaseController {

    @GetMapping("/queryUserPage")
    @ApiOperation(value = "获取列表")
    public Map queryUserPage(int status,int sex,long ProvinceID,long CityID,long AreaID,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(sex!=-1){
            queryMap.put("Sex", sex);
        }
        if(ProvinceID!=0){
            queryMap.put("ProvinceID", ProvinceID);
        }
        if(CityID!=0){
            queryMap.put("CityID", CityID);
        }
        if(AreaID!=0){
            queryMap.put("AreaID", AreaID);
        }
        PageInfo<User> page = this.ReadUserService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setUserStatus")
    @ApiOperation(value = "设置状态")
    public Map setUserStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        User temp=JSON.parseObject(data,User.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                UserService.recoverByID(id);
            }else{
                UserService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findUser/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findUser(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        User temp=ReadUserService.findById(id);
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


    @PostMapping("/modifyUser")
    @ApiOperation(value = "修改")
    public Map modifyUser(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        User temp = JSON.parseObject(data, User.class);
        User  obj=new User();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadUserService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setLoginName(temp.getLoginName());
        obj.setPassWord(temp.getPassWord());
        obj.setHeadImg(temp.getHeadImg());
        obj.setName(temp.getName());
        obj.setNikeName(temp.getNikeName());
        obj.setSignature(temp.getSignature());
        obj.setSex(temp.getSex());
        obj.setAge(temp.getAge());
        obj.setPhone(temp.getPhone());
        obj.setUnionID(temp.getUnionID());
        obj.setOpenID(temp.getOpenID());
        obj.setEmail(temp.getEmail());
        obj.setProvinceID(temp.getProvinceID());
        obj.setAreaID(temp.getAreaID());
        obj.setCityID(temp.getCityID());
        obj.setLongAddress(temp.getLongAddress());
        obj.setAddress(temp.getAddress());


        User tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=UserService.insert(obj);
        }else{
            tempObj=UserService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
