package com.code.config.filter;

import com.code.service.read.ReadAuthorizeService;
import com.code.service.read.ReadOnlineService;
import com.code.until.CommonStatus;
import com.code.until.CommonUntil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MaJian on 18/5/1.
 * @author majian
 *
 * 过滤器
 */
@Log4j
@Order(2)
@Configuration
public class CustomFilter implements Filter{
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Autowired
    private ReadAuthorizeService ReadAuthorizeService;
    @Autowired
    private ReadOnlineService ReadOnlineService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CustomFilter初始化......");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //执行操作后必须doFilter
        boolean Redirect=false;
        try {
            this.request = (HttpServletRequest) servletRequest;
            this.response = (HttpServletResponse) servletResponse;

            String requestUrl = this.request.getRequestURI().replace(this.request.getContextPath(), "");
           // log.info("访问地址"+requestUrl);
            //静态文件直接放行
            if(CommonUntil.dimAuthorize(requestUrl)){return;}

            //如果是所有平台的,直接放行
            List<String> authorize=ReadAuthorizeService.getAuthorizeStringList(CommonStatus.AuthorizeType.All.seq);
            if(authorize.contains(requestUrl)){return;}

            //逐个验证
            int Authorize= CommonUntil.checkAuthorize(requestUrl);
            //获取验证信息头部信息
            String token=request.getHeader("Token");
            //接口验证
            if(Authorize==CommonStatus.AuthorizeType.Interface.seq){
                authorize=ReadAuthorizeService.getAuthorizeStringList(CommonStatus.AuthorizeType.Interface.seq);
                if(!authorize.contains(requestUrl)){
                    if(CommonUntil.getInstance().CheckToken(token,request, ReadOnlineService)){
                        return;
                    }
                }
                return;
            //后台
            }else if(Authorize==CommonStatus.AuthorizeType.Admin.seq){
                authorize=ReadAuthorizeService.getAuthorizeStringList(CommonStatus.AuthorizeType.Admin.seq);
                if(!authorize.contains(requestUrl)){
                    if(!CommonUntil.getInstance().CheckToken(token,request, ReadOnlineService)){
                        response.sendRedirect(request.getContextPath()+"/Error/errorToken");
                        Redirect=true;
                        return;
                    }
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(!Redirect){
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }
    @Override
    public void destroy() {
        log.info("CustomFilter过滤器销毁......");
    }


}
