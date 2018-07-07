package com.zhangrui.tomcat;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Desp:
 * 2018-05-19 22:46
 * Created by zhangrui.
 */
public class StandHost {

    private String host;
    private String appBase;

    private Map<String, StandContext> standContexts = new HashMap<>();

    public StandHost(String host, String appBase) {
        this.appBase = appBase;
        this.host = host;
    }

    public void start() throws Exception{
        List<String> projects = new ArrayList<>();
        File file = new File(this.appBase);
        File[] files = file.listFiles();
        //加载项目
        for (File project : files){
            if (project.isDirectory()){  //
                System.out.println("Find Project --> " + project.getName());
                projects.add(project.getName());
            }
        }

        if (projects.size() == 0) return;

        //解析项目
        for (String projectName : projects){
            StandContext standContext = new StandContext(appBase + "\\" + projectName);
            standContext.start();
            standContexts.put("/" + projectName, standContext);
        }

    }


    /**
     * 业务Socket请求时候的真正执行方式
     * @param request
     * @param response
     * @throws Exception
     */
    public void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        StandContext context = standContexts.get(contextPath);
        if (context == null){
            return;
        }
        Map<String, Servlet> urlPath2ServletInstance = context.getUrlPath2ServletInstance();

        Servlet servlet = urlPath2ServletInstance.get(servletPath);
        servlet.service(request, response);
    }
}
