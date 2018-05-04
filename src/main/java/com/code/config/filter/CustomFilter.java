package com.code.config.filter;

import com.alibaba.fastjson.JSONException;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CustomFilter初始化......");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //执行操作后必须doFilter
        try {
            this.request = (HttpServletRequest) servletRequest;
            this.response = (HttpServletResponse) servletResponse;
            String url=this.request.getRequestURI();
            log.info("url:"+url);
        } catch (Exception e) {

        } finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
    @Override
    public void destroy() {
        log.info("CustomFilter过滤器销毁......");
    }
}
