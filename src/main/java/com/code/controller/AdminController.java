package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.domain.Online;
import com.code.domain.Return;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Admin;
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
@Api("Admin")
@RestController
@RequestMapping("/Admin")
public class AdminController extends BaseController {


    @GetMapping("/queryAdminPage")
    @ApiOperation(value = "获取列表")
    public Map queryAdminPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Admin> page = this.ReadAdminService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setAdminStatus")
    @ApiOperation(value = "设置状态")
    public Map setAdminStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Admin temp=JSON.parseObject(data,Admin.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                AdminService.recoverByID(id);
            }else{
                AdminService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findAdmin/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findAdmin(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Admin temp=ReadAdminService.findById(id);
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


    @PostMapping("/modifyAdmin")
    @ApiOperation(value = "修改")
    public Map modifyAdmin(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Admin temp = JSON.parseObject(data, Admin.class);
        Admin  obj=new Admin();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadAdminService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setName(temp.getName());
        obj.setModifyTime(temp.getModifyTime());
        obj.setHeadImg(temp.getHeadImg());
        obj.setLoginName(temp.getLoginName());
        obj.setPassWord(temp.getPassWord());
        obj.setPhone(temp.getPhone());
        obj.setProvinceID(temp.getProvinceID());
        obj.setAreaID(temp.getAreaID());
        obj.setCityID(temp.getCityID());
        obj.setLongAddress(temp.getLongAddress());
        obj.setAddress(temp.getAddress());


        Admin tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=AdminService.insert(obj);
        }else{
            tempObj=AdminService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

    @ApiOperation(value = "管理员登录")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "string", name = "data", value = "{'username':'admin','password':'123'} username:用户名 password:密码", required = true) })
	@PostMapping("/loginName")
	public Return<Admin> login(String data,HttpServletRequest request){
		Return returnMap=new Return();
		Map<String,Object> queryMap=new HashMap<String, Object>();
		Admin admin=JSON.parseObject(data,Admin.class);
        if(CommonUntil.CheckIsNull(admin.getLoginName())&&CommonUntil.CheckIsNull(admin.getPassWord())){
            queryMap.put(Admin.COLUMN_LoginName,admin.getLoginName());
            queryMap.put(Admin.COLUMN_PassWord,admin.getPassWord());
            queryMap.put(Admin.COLUMN_Status,CommonStatus.Status.Ectivity.getid());
            List<Admin> admins=ReadAdminService.query(queryMap);
            if(admins.size()>0){
                admin=admins.get(0);
                String sessionID=request.getSession().getId();
                Online Online=CommonUntil.getInstance().CreateOnline(sessionID,admin.getID(),CommonStatus.OnLineType.Admin.seq,ReadOnlineService,OnlineService);
                if(Online!=null){
                    returnMap=CommonUntil.ReturnMapWithToken(0,"登录成功",admins.get(0),sessionID);
                }else{
                    returnMap=CommonUntil.ReturnMap(-1,"登陆异常请稍后重试",admin);
                }
            }else{
                returnMap=CommonUntil.ReturnMap(-1,"验证码或者密码错误",admin);
            }
        }else{
            returnMap=CommonUntil.ReturnMap(-1,"登录名或密码不能为空",admin);
        }
		return returnMap;
	}
}
