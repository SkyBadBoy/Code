//package com.code.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.code.domain.Admin;
//import com.code.domain.Return;
//import com.code.until.CommonUntil;
//import io.swagger.annotations.*;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
///**
// * 后台的接口
// */
//@Api(value = "管理员模块")
//@RestController
//@RequestMapping("/admin")
//public class AdminController extends BaseController{
//
//	@Autowired
//	private CacheManager cacheManager;
//	/**
//	 * 后台登录接口
//	 * @param data
//	 * @return
//     */
//	@ApiOperation(value = "管理员登录")
//	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "string", name = "data", value = "{'username':'admin','password':'123'} username:用户名 password:密码", required = true) })
//	@PostMapping("/login")
//	public Return<Admin> login(String data){
//		Return returnMap=new Return();
//		Map<String,Object> queryMap=new HashMap<String, Object>();
//		Admin admin=JSON.parseObject(data,Admin.class);
//		queryMap.put("username",admin.getUsername());
//		queryMap.put("password",admin.getPassword());
//		List<Admin> admins=adminService.query(queryMap);
//		if(admins.size()>0){
//			returnMap=CommonUntil.ReturnMap(0,"登录成功",admins.get(0));
//		}else{
//			returnMap=CommonUntil.ReturnMap(-1,"验证码或者密码错误",new Admin());
//		}
//		return returnMap;
//	}
//
//
//
//
//
//}
