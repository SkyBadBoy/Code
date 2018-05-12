package com.code.controller;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付相关类
 */
@RestController
@RequestMapping("/WeChat")
public class WeChatController extends BaseController{


	/**
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *  微信支付的回调
     */
	@RequestMapping(value = "/payCallBack",  produces = "text/html;charset=UTF-8",method={RequestMethod.GET,RequestMethod.POST})
	public  String payCallBack(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Logger logger = Logger.getLogger(WeChatController.class);
		logger.info("微信服务号支付回调");
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream() ;
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList) {
			map.put(e.getName(), e.getText());
		}

		JSONObject json = JSONObject.parseObject(JSON.toJSONString(map));

		logger.info("===消息通知的结果：" + json.toString() + "==========================");
		logger.info("===return_code===" + map.get("return_code"));
		logger.info("===return_msg===" + map.get("return_msg"));
		logger.info("===out_trade_no===" + map.get("out_trade_no"));

		if(map.get("return_code").equals("SUCCESS")) {

			String total_fee=json.getString("total_fee");
			total_fee = new java.text.DecimalFormat("#0.00").format(Double.parseDouble(total_fee) / 100);// 支付金额以分为单位
			String orderID = map.get("out_trade_no");//订单编号
			String AppID=json.getString("appid");
			String bank_type=json.getString("bank_type");

			JSONObject data=new JSONObject();
			data.put("out_trade_no",orderID);
			data.put("bank_type",bank_type);
			data.put("total_fee",total_fee);
			data.put("appid",AppID);

//			Box b= JSON.parseObject(json.getString("attach"),Box.class);
//			b.setId(CommonUntil.CreateNewID());
//			b.setStatus(CommonStatus.Status.Ectivity.getid());
//			b.setType(CommonStatus.Status.Run.getid());
//			int place = new Random().nextInt(Integer.parseInt(b.getNum())) + 1;
//			b.setPlace(place+"");
//			b.setMoney(total_fee);
//			b.setData(JSON.toJSONString(data));
//			Box bo=boxService.insert(b);

			// 释放资源
			inputStream.close();
			inputStream = null;

			//bis.close();
			return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";//成功后给微信返回数据
		}
		if (map.get("return_code").equals("FAIL")) {

			/**
			 *支付失败后的业务处理
			 */
			logger.info("==============================");
			logger.info("支付失败");
			logger.info("==============================");
			// 释放资源
			inputStream.close();

			inputStream = null;

			return "FAIL";
		}
		// 释放资源
		inputStream.close();
		inputStream = null;
		return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";//成功后给微信返回数据
	}







}
