package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.code.domain.*;
import com.code.service.BoxService;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 地区相关类
 */
@RestController
@RequestMapping("/region")
@Api(value = "地区模块")
public class RegionController extends BaseController{

	@RequestMapping("/queryPage")
	public Map<String,Object> queryPage(long parentid,String search,int pageNumber,int pageSize){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		PageInfo<Region> page = getRegionList(parentid,search,pageNumber,pageSize);
		returnMap.put("rows",page.getList());
		returnMap.put("total",page.getTotal());
		return returnMap;
	}

	private PageInfo<Region> getRegionList(long parentid,String search,int pageNumber,int pageSize){
		Map<String,Object> queryMap=new HashMap<String,Object>();
		if (CommonUntil.CheckIsNull(search)){
			queryMap.put("search",search);
		}
		if(parentid!=0){
			queryMap.put("parentid",parentid);
		}
		PageInfo<Region> page = this.regionService.queryPage(queryMap, pageNumber, pageSize);
		return page;
	}


}
