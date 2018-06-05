package com.zhangrui.annotation.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "name",value = "zhangrui")})
public class ConfigFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(filterConfig.getInitParameter("name"));
        String path = filterConfig.getServletContext().getContextPath();
        filterConfig.getServletContext().log("------请求的路径ContextPath是： " + path);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
