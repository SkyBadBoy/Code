package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Region;
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
@Api("Region")
@RestController
@RequestMapping("/Region")
public class RegionController extends BaseController {


    @GetMapping("/queryRegionPage")
    @ApiOperation(value = "获取列表")
    public Map queryRegionPage(int status,long ProvinceID,long CityID,long AreaID,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if (status != -1) {
            queryMap.put("Status", status);
        }
        if (ProvinceID != 0) {
            queryMap.put("ProvinceID", ProvinceID);
        }
        if (CityID != 0) {
            queryMap.put("CityID", CityID);
        }
        if (AreaID != 0) {
            queryMap.put("AreaID", AreaID);
        }
        PageInfo<Region> page = this.ReadRegionService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }
    @GetMapping("/queryRegionByParentID")
    @ApiOperation(value = "获取列表")
    public Map queryRegionByParentID(long ParentID,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("Status", 1);
        queryMap.put("ParentID",ParentID);
        List<Region> page = this.ReadRegionService.query(queryMap);
        returnMap.put("data", page);
        return returnMap;
    }
    @PostMapping("/setRegionStatus")
    @ApiOperation(value = "设置状态")
    public Map setRegionStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Region temp=JSON.parseObject(data,Region.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                RegionService.recoverByID(id);
            }else{
                RegionService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findRegion/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findRegion(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Region temp=ReadRegionService.findById(id);
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


    @PostMapping("/modifyRegion")
    @ApiOperation(value = "修改")
    public Map modifyRegion(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Region temp = JSON.parseObject(data, Region.class);
        Region  obj=new Region();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadRegionService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCode(temp.getCode());
        obj.setName(temp.getName());
        obj.setParentID(temp.getParentID());
        obj.setLevel(temp.getLevel());
        obj.setOrder(temp.getOrder());
        obj.setNameEn(temp.getNameEn());
        obj.setShortNameEn(temp.getShortNameEn());
        obj.setProvinceID(temp.getProvinceID());
        obj.setProvinceName(temp.getProvinceName());
        obj.setCityID(temp.getCityID());
        obj.setCityName(temp.getCityName());
        obj.setAreaID(temp.getAreaID());
        obj.setAreaName(temp.getAreaName());


        Region tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=RegionService.insert(obj);
        }else{
            tempObj=RegionService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
