package com.code.until.wechat;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;


public class WXUtil {
	
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	public static String getWeChatAttach(HttpServletRequest req,int type,String userid,String belong){
		JSONObject json=new JSONObject();
		if(userid==null || userid.isEmpty()){
			userid = "0";
		}
		json.put("type", type);
		json.put("uid", userid);
		json.put("bid", belong);
		String jsonStr=json.toJSONString();
		jsonStr =jsonStr.replaceAll("\"", "'");
		return jsonStr;
		
	}
}
