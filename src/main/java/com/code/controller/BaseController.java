package com.code.controller;


import com.code.config.rabbit.RabbitUtil;
import com.code.domain.Error;
import com.code.service.write.*;
import com.code.service.read.*;
import com.code.until.CommonUntil;
import org.springframework.amqp.core.AmqpTemplate;
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
	protected WeChatInfoService weChatInfoService;
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
	protected RegionService RegionService;
	@Autowired
	protected ReportService ReportService;
	@Autowired
	protected FeedbackService FeedbackService;
	@Autowired
	protected CrimeService CrimeService;
	@Autowired
	protected UserService UserService;
	@Autowired
	protected OnlineService OnlineService;
	@Autowired
	protected AuthorizeService AuthorizeService;
	@Autowired
	protected AdminService AdminService;
	@Autowired
	protected OperationService OperationService;
	@Autowired
	protected PowerService PowerService;
	@Autowired
	protected BaseinfoService BaseinfoService;
	@Autowired
	protected ArticleService ArticleService;
	@Autowired
	protected HelpService HelpService;
	@Autowired
	protected BlacklistService BlacklistService;
	@Autowired
	protected DepartmentService DepartmentService;
	@Autowired
	protected PostService PostService;

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
	@Autowired
	protected ReadRegionService ReadRegionService;
	@Autowired
	protected ReadReportService ReadReportService;
	@Autowired
	protected ReadFeedbackService ReadFeedbackService;
	@Autowired
	protected ReadCrimeService ReadCrimeService;
	@Autowired
	protected ReadUserService ReadUserService;
	@Autowired
	protected ReadOnlineService ReadOnlineService;
	@Autowired
	protected ReadAuthorizeService ReadAuthorizeService;
	@Autowired
	protected ReadAdminService ReadAdminService;
	@Autowired
	protected ReadOperationService ReadOperationService;
	@Autowired
	protected ReadPowerService ReadPowerService;
	@Autowired
	protected ReadBaseinfoService ReadBaseinfoService;
	@Autowired
	protected ReadArticleService ReadArticleService;
	@Autowired
	protected ReadHelpService ReadHelpService;
	@Autowired
	protected ReadBlacklistService ReadBlacklistService;
	@Autowired
	protected ReadDepartmentService ReadDepartmentService;
	@Autowired
	protected ReadPostService ReadPostService;




	@Autowired
	protected AmqpTemplate RabbitTemplate;

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
		error.setID(CommonUntil.getInstance().CreateNewID());
		error.setClassName(clazz);
		error.setName(method);
		error.setMessage(exception.substring(0,exception.length()>200?200:exception.length()));
		error.setStatus(1);
		RabbitUtil.getInstance().ErrorLog(error,ErrorService,RabbitTemplate);
		System.err.println(exception);
		request.getRequestDispatcher("/Error").forward(request, response);
	}
}
