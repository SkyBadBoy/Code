package com.code.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.code.domain.Return;
import com.code.domain.WeChatInfo;
import com.code.until.NetUtil;
import com.code.domain.User;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户先关类
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{


	/**
	 * 用户授权接口
	 * @param data
	 * @return
     */
	@PostMapping("/login")
	@ResponseBody
	public Return login(String data){
		Return returnMap=new Return();
		User use= JSON.parseObject(data,User.class);
		if (!CommonUntil.CheckIsNull(use.getOpenid())){
			return CommonUntil.ReturnMap(-1,"用户信息授权异常",null);
		}
		User uses=userService.findByOpenid(use.getOpenid());
		if (uses!=null){
			returnMap=CommonUntil.ReturnMap(0,"登录成功",uses);
		}else{
			use.setId(CommonUntil.CreateNewID());
			use.setStatus(CommonStatus.Status.Ectivity.getid());
			use =userService.insertUser(use);
			returnMap=CommonUntil.ReturnMap(0,"登录成功",use);
		}
		return returnMap;
	}

	/**
	 * 小程序获取用户的信息接来
	 * @param code
	 * @param request
     * @return
     */
	@ResponseBody
	@GetMapping("getOpenid")
	public JSONObject getOpenid(@RequestParam(value="code") String code,HttpServletRequest request) {
		String url="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
		WeChatInfo weChatInfo=CommonUntil.getWeChatInfo(weChatInfoService);
		return NetUtil.doGet(url.replace("APPID", weChatInfo.getAppid()).replace("SECRET", weChatInfo.getAppsecret()).replace("JSCODE", code));
	}


	/**
	 * 获取用户列表接口
	 * @param status
	 * @param search
	 * @param pageNumber
	 * @param pageSize
     * @return
     */
	@RequestMapping("/queryPage")
	@ResponseBody
	public Map<String,Object> queryPage(int status,String search,int pageNumber,int pageSize){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("search",search);
		if(status!=0){
			queryMap.put("status",status);
		}
		PageInfo<User> page = this.userService.queryPage(queryMap, pageNumber, pageSize);
		returnMap.put("rows",page.getList());
		returnMap.put("total",page.getTotal());
		return returnMap;
	}


	/**
	 * 设置用户的状态接口
	 * @param data
	 * @return
     */
	@RequestMapping("/setStatus")
	@ResponseBody
	public Map<String,Object> setStatus(String data){
		Map<String,Object> returnMap=new HashMap<String,Object>();
		User user=JSON.parseObject(data,User.class);
		String[] ids=user.getId().split(",");
		for (String id : ids){
			if(Integer.parseInt(user.getStatus())==Integer.parseInt(CommonStatus.Status.Ectivity.getid()) ){
				userService.recoverById(id);
			}else{
				userService.deleteById(id);
			}
		}
		returnMap.put("code",0);
		returnMap.put("message","操作成功");
		return returnMap;
	}

}
