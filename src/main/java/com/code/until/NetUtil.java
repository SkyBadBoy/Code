package com.code.until;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;



public class NetUtil {


	public static com.alibaba.fastjson.JSONObject doGet(String url) {
		String result = "";
		com.alibaba.fastjson.JSONObject json = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			String urlstring = url;
			URL realurl = new URL(urlstring);
			System.out.println("请求的服务器主机域名：" + realurl.getHost().toString());
			// 打开与此URL的连接
			URLConnection connection = realurl.openConnection();
			// 设置请求连接时间和读取数据时间
			connection.setConnectTimeout(7000);
			connection.setReadTimeout(17000);

			// 建立实际的连接
			connection.connect();
			// out = new PrintWriter(connection.getOutputStream());
			// // 发送参数
			// out.print(param);
			// // 输出流的缓冲
			// out.flush();
			// 读取获取的数据

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			json = JSON.parseObject(result);
		} catch (Exception e) {
			json = new com.alibaba.fastjson.JSONObject();
			json.put("data", result);
			System.out.println("发送GET请求出现异常！" + e);
			//e.printStackTrace();
			//ErrorUtil.HandleError(null, "wtb.smUtil.doGet", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				System.out.println("关闭请求流出现异常！" + e2);
				e2.printStackTrace();

			}
			return json;

		}



	}
}
