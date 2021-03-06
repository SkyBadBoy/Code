package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.config.rabbit.RabbitUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Menu;
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
@Api("Menu")
@RestController
@RequestMapping("/Menu")
public class MenuController extends BaseController {


    @GetMapping("/queryMenuPage")
    @ApiOperation(value = "获取列表")
    public Map queryMenuPage(int status,String search,long parentID,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(parentID!=0){
            queryMap.put(Menu.COLUMN_ParentID, parentID);
        }
        queryMap.put(Menu.COLUMN_Type, 1);
        PageInfo<Menu> page = this.ReadMenuService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Menu-queryMenuPage】列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/queryMenuContentAll")
    @ApiOperation(value = "获取所有的目录列表")
    public Map queryMenuContentAll(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        queryMap.put(Menu.COLUMN_Type, 0);
        List<Menu> page = this.ReadMenuService.query(queryMap);
        for (Menu sub:page) {
            queryMap.clear();
            queryMap.put(Menu.COLUMN_ParentID,sub.getID());
            queryMap.put(Menu.COLUMN_Status,1);
            PageInfo<Menu> subMenu = this.ReadMenuService.queryPage(queryMap, 0, 0);
            sub.setSubMenu(subMenu.getList());
        }
        returnMap.put("data",page);
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查看【Menu-queryMenuContentAll】目录列表",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/setMenuStatus")
    @ApiOperation(value = "设置状态")
    public Map setMenuStatus(String data,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Menu temp=JSON.parseObject(data,Menu.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                MenuService.recoverByID(id);
            }else{
                MenuService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"设置【Menu-setMenuStatus】状态",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

    @GetMapping("/findMenu/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findMenu(@PathVariable("id") String id,HttpServletRequest request){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Menu temp=ReadMenuService.findById(id);
        if(temp!=null){
	   		returnMap.put("code",0);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取成功");
    	}else{
			returnMap.put("code",-1);
	        returnMap.put("data",temp);
	        returnMap.put("message","获取失败");
		}
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"查询【Menu-findMenu-"+id+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }


    @PostMapping("/modifyMenu")
    @ApiOperation(value = "修改")
    public Map modifyMenu(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Menu temp = JSON.parseObject(data, Menu.class);
        Menu  obj=new Menu();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadMenuService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setName(temp.getName());
        obj.setMemo(temp.getMemo());
        obj.setType(temp.getType());
        obj.setParentID(temp.getParentID());
        obj.setLogo(temp.getLogo());
        obj.setUrl(temp.getUrl());
        obj.setOrder(temp.getOrder());
//        obj.setAdminID(temp.getAdminID());
        obj.setEnd(temp.getEnd());
        obj.setUrlType(temp.getUrlType());


        Menu tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.getInstance().CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=MenuService.insert(obj);
        }else{
            tempObj=MenuService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        RabbitUtil.getInstance().OperationLog(request.getHeader("Token"),"修改【Menu-modifyMenu-"+obj.getID()+"】内容",ReadOnlineService,OperationService,RabbitTemplate,ReadUserService);
        return returnMap;
    }

}
