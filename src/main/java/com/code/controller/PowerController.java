package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.code.domain.Menu;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Power;
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
@Api("Power")
@RestController
@RequestMapping("/Power")
public class PowerController extends BaseController {


    @GetMapping("/queryPowerPage")
    @ApiOperation(value = "获取列表")
    public Map queryPowerPage(int status,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        PageInfo<Power> page = this.ReadPowerService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Power-queryPowerPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/queryPowerAll")
    @ApiOperation(value = "获取所有的目录列表 Type = 0 根据分组  1  不根据分组")
    public Map queryPowerAll(String RoleID,int Type,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        if(Type==0){
            queryMap.put(Power.COLUMN_Type,Type);
        }
        queryMap.put(Power.COLUMN_RoleID,RoleID);
        PageInfo<Power> page = this.ReadPowerService.queryPage(queryMap, 0, 0);
        if(Type==0){
            List<Power> pageList=page.getList();
            for(Power power:pageList){
                queryMap.clear();
                queryMap.put(Power.COLUMN_Status,CommonStatus.Status.Ectivity.getid());
                queryMap.put(Power.COLUMN_ParentID,power.getID());
                PageInfo<Power> sub = this.ReadPowerService.queryPage(queryMap, 0, 0);
                power.setSubPower(sub.getList());
            }
        }
        returnMap.put("data",page.getList());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Power-queryPowerAll】目录列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/setPowerStatus")
    @ApiOperation(value = "设置状态")
    public Map setPowerStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Power temp=JSON.parseObject(data,Power.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                PowerService.recoverByID(id);
            }else{
                PowerService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Power-setPowerStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @PostMapping("/setPower")
    @ApiOperation(value = "设置权限")
    public Map setPower(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        JSONObject jsonObject=JSON.parseObject(data);
        String RoleID=jsonObject.getString("RoleID");
        JSONArray ShowList=jsonObject.getJSONArray("ShowList");
        //删除当前角色拥有的所有缺陷
        queryMap.put(Power.COLUMN_RoleID,RoleID);
        queryMap.put(Power.COLUMN_Status,1);
        PowerService.deleteByCondition(queryMap);
        //重新遍历赋予权限
        for(int i=0;i<ShowList.size();i++){
            //第一层的数据
            JSONObject fastData=ShowList.getJSONObject(i);
            //获取菜单的编号
            String menuID=fastData.getString("id");
            boolean check=fastData.getBoolean("check");
            //查询更新
            if(check){
                CommonUntil.getInstance().CheckPower(menuID,RoleID,ReadPowerService,PowerService,ReadMenuService);
            }

            //第二层的数据遍历
            JSONArray secondList=fastData.getJSONArray("subShow");
            for(int j=0;j<secondList.size();j++){
                JSONObject secondData=secondList.getJSONObject(j);
                //获取菜单的编号
                String secondMenuID=secondData.getString("id");
                //查询更新
                boolean secondCheck=secondData.getBoolean("check");
                if (secondCheck) {
                    CommonUntil.getInstance().CheckPower(secondMenuID,RoleID,ReadPowerService,PowerService,ReadMenuService);
                }

            }
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Power-setPower】权限",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @GetMapping("/findPower/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findPower(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Power temp=ReadPowerService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Power-findPower-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyPower")
    @ApiOperation(value = "修改")
    public Map modifyPower(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Power temp = JSON.parseObject(data, Power.class);
        Power  obj=new Power();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadPowerService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setParentID(temp.getParentID());
        obj.setOrder(temp.getOrder());
        obj.setAdminID(temp.getAdminID());
        obj.setEnd(temp.getEnd());
        obj.setMenuID(temp.getMenuID());
        obj.setRoleID(temp.getRoleID());


        Power tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=PowerService.insert(obj);
        }else{
            tempObj=PowerService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Power-modifyPower-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
