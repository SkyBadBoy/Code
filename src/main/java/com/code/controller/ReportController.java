package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import com.code.domain.Report;
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
@Api("Report")
@RestController
@RequestMapping("/Report")
public class ReportController extends BaseController {


    @GetMapping("/queryReportPage")
    @ApiOperation(value = "获取列表")
    public Map queryReportPage(int status,int type,int anonymity,String search,int pageNumber, int pageSize,HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(3);
        queryMap.put("search", search);
        if(status!=-1){
            queryMap.put("Status", status);
        }
        if(type!=-1){
            queryMap.put("Type", type);
        }
        if(anonymity!=-1){
            queryMap.put("Anonymity", anonymity);
        }
        PageInfo<Report> page = this.ReadReportService.queryPage(queryMap, pageNumber, pageSize);
        returnMap.put("rows", page.getList());
        returnMap.put("total", page.getTotal());
        return returnMap;
    }

    @PostMapping("/setReportStatus")
    @ApiOperation(value = "设置状态")
    public Map setReportStatus(String data){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Report temp=JSON.parseObject(data,Report.class);
        String[] ids=temp.getID().split(",");
        for (String id : ids){
            if(temp.getStatus()==Integer.parseInt(CommonStatus.Status.Ectivity.getid())){
                ReportService.recoverByID(id);
            }else{
                ReportService.deleteById(id);
            }
            queryMap.clear();
        }
        returnMap.put("code",0);
        returnMap.put("message","操作成功");
        return returnMap;
    }

    @GetMapping("/findReport/{id}")
    @ApiOperation(value = "根据编号查询内容")
    public Map findReport(@PathVariable("id") String id){
        Map<String,Object> returnMap=new HashMap<>(2);
        Map<String,Object> queryMap=new HashMap<>(1);
        Report temp=ReadReportService.findById(id);
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


    @PostMapping("/modifyReport")
    @ApiOperation(value = "修改")
    public Map modifyReport(String data, HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Report temp = JSON.parseObject(data, Report.class);
        Report  obj=new Report();
        boolean isNew=false;
        if("0".equals(temp.getID())){
            isNew=true;
        }else{
            obj=ReadReportService.findById(String.valueOf(temp.getID()));
            if(obj==null){
                isNew=true;
            }
        }

        obj.setCreateTime(temp.getCreateTime());
        obj.setStatus(temp.getStatus());
        obj.setUserID(temp.getUserID());
        obj.setAnonymity(temp.getAnonymity());
        obj.setContent(temp.getContent());
        obj.setSrcID(temp.getSrcID());
        obj.setType(temp.getType());


        Report tempObj=null;
        if(isNew){
            obj.setID(CommonUntil.CreateNewID());
            obj.setStatus(Integer.parseInt(CommonStatus.Status.Ectivity.getid()));
            tempObj=ReportService.insert(obj);
        }else{
            tempObj=ReportService.update(obj);
        }
        returnMap.put("code", tempObj!=null?0:-1);
        returnMap.put("message", tempObj!=null?"修改成功":"修改失败");
        return returnMap;
    }

}
