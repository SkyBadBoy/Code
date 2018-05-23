package com.code.config.cross;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MaJian on 18/4/24.
 * @author 支持跨域
 */
@Configuration
public class Cross extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json");
        //允许所以域名访问，*需要换位制定来源，务必注意这里
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //允许的访问方式
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,auth_token");
        response.setHeader("Access-Control-Request-Headers", "x-requested-with,content-type,Accept");

        //以下参数解决由于跨域问题造成的sessIon不一致问题
        /**
         *
         * 前端需设置
         * axios.defaults.withCredentials=true;
         *   xhrFields:{
                withCredentials: true
             },
                crossDomain: true
             })
         *
         * */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");

        filterChain.doFilter(request, response);
    }
}
