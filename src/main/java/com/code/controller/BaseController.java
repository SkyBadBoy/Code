package com.code.controller;


import com.code.service.write.*;
import com.code.service.read.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 基础控制器
 */
@Controller
public class BaseController {
	@Autowired
	protected AdminService adminService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected BoxService boxService;
	@Autowired
	protected RecordService recordService;
	@Autowired
	protected WeChatInfoService weChatInfoService;
	@Autowired
	protected RegionService regionService;

	@Autowired
	protected ReadBoxService ReadBoxService;
	//@ExceptionHandler
	public void exp(HttpServletRequest request, HttpServletResponse response, Exception ex, HttpSession session) throws ServletException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		String clazz = ex.getClass().getName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		for(StackTraceElement stack:ex.getStackTrace()){
			if(stack.getClassName()!=null && stack.getClassName().contains("wtb.")){
				clazz=stack.getClassName();
				method=stack.getMethodName();
				break;
			}
		}
		//ErrorUtil.HandleError2(String.valueOf(myself != null ? myself.getID() : 0), clazz+"."+method, ex,exception);
		request.getRequestDispatcher("/error/500.html").forward(request, response);
	}
}
