package com.code.controller;


import com.code.domain.Error;
import com.code.service.write.*;
import com.code.service.read.*;
import com.code.until.CommonUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	protected ErrorService ErrorService;
	@Autowired
	protected KeywordService KeywordService;
	@Autowired
	protected AccessService AccessService;
	@Autowired
	protected RoleService RoleService;
	@Autowired
	protected MenuService MenuService;
	@Autowired
	protected ScreenService ScreenService;

	@Autowired
	protected ReadBoxService ReadBoxService;
	@Autowired
	protected ReadActivityService ReadActivityService;
	@Autowired
	protected ActivityService ActivityService;
	@Autowired
	protected ReadErrorService ReadErrorService;
	@Autowired
	protected ReadKeywordService ReadKeywordService;
	@Autowired
	protected ReadAccessService ReadAccessService;
	@Autowired
	protected ReadRoleService ReadRoleService;
	@Autowired
	protected ReadMenuService ReadMenuService;
	@Autowired
	protected ReadScreenService ReadScreenService;


	@ExceptionHandler
	public void exp(HttpServletRequest request, HttpServletResponse response, Exception ex, HttpSession session) throws ServletException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(baos));
		String exception = baos.toString();
		String clazz = ex.getClass().getName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		for(StackTraceElement stack:ex.getStackTrace()){
			if(stack.getClassName()!=null && stack.getClassName().contains("com.code")){
				clazz=stack.getClassName();
				method=stack.getMethodName();
				break;
			}
		}
		Error error=new Error();
		error.setID(CommonUntil.CreateNewID());
		error.setClassName(clazz);
		error.setName(method);
		error.setMessage(exception.substring(0,exception.length()>200?200:exception.length()));
		error.setStatus(1);
		ErrorService.insert(error);
		System.err.println(exception);
		request.getRequestDispatcher("/Error").forward(request, response);
	}
}
