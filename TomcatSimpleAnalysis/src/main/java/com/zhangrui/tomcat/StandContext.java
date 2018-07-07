package com.zhangrui.tomcat;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Desp:对应Servlet，用于解析Servlet
 * 2018-05-19 22:46
 * Created by zhangrui.
 */
public class StandContext extends DefaultHandler {

    /** web.xml中的servletName->ServletClass **/
    private Map<String, String> servletName2ServletClass = new HashMap<>();
    /** web.xml中的servletName->ServletConfig **/
    private Map<String, Map<String, String>> servletInitConfig = new HashMap<>();
    /** web.xml中的servletName->urlPath **/
    private Map<String, String> servletName2UrlPath = new HashMap<>();
    /** web.xml中的urlPath->Servlet实例 **/
    private Map<String, Servlet> urlPath2ServletInstance = new HashMap<>();

    private String appPath;

    public StandContext(String appPath){
        this.appPath = appPath;
    }

    public Map<String, String> getServletName2UrlPath() {
        return servletName2UrlPath;
    }

    public Map<String, Servlet> getUrlPath2ServletInstance() {
        return urlPath2ServletInstance;
    }

    public void start() throws Exception{
        //解析web.xml
        loadXml(appPath + "\\WEB-INF\\web.xml");

        //加载Servlet
        loadServlet();

    }

    /**
     * 关键点：自定义ClassLoader
     * @throws Exception
     */
    private void loadServlet() throws Exception{

        for (Map.Entry<String, String> entry : servletName2UrlPath.entrySet()){
            final String servletName = entry.getKey();
            String urlPath = entry.getValue();
            String servletClass = servletName2ServletClass.get(servletName);
            if (servletClass == null){
                return;
            }

            /** 加载所有的lib文件和classes中的文件，用于自定义类加载器 **/
            ArrayList<URL> urls = new ArrayList<>();
            File libs = new File(this.appPath + "\\WEB-INF\\lib");
            if (libs.exists()){
                for (String lib : libs.list()){
                    //URL的使用方式
                    URL url = new URL("file:" + this.appPath + "\\WEB-INF\\lib\\" + lib);
                    urls.add(url);
                }
            }

            urls.add(new URL("file:" + this.appPath + "\\WEB-INF\\classes\\"));

            /** 自定义classLoader,借用UrlClassLoader **/
            URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]), this.getClass().getClassLoader());
            /** 设置当前线程的ClassLoader为主Loader---UrlClassLoader **/
            Thread.currentThread().setContextClassLoader(classLoader);

            Class<?> clazz = Class.forName(servletClass, true, classLoader);
            Servlet instance = (Servlet) clazz.newInstance();

            instance.init(new ServletConfig() {
                @Override
                public String getServletName() {
                    return servletName;
                }

                @Override
                public ServletContext getServletContext() {
                    return null;
                }

                @Override
                public String getInitParameter(String s) {
                    return servletInitConfig.get(servletName).get(s);
                }

                @Override
                public Enumeration<String> getInitParameterNames() {
                    return null;
                }
            });

            urlPath2ServletInstance.put(urlPath, instance);


        }

    }

    private void loadXml(String webXml) {
        try {
            SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            SAXParser parser = saxFactory.newSAXParser();
            parser.parse(webXml, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String qName = null;
    private String servletName = null;
    private String servletClass = null;
    private String paramName = null;
    private String paramValue = null;
    private String urlPath = null;

    @Override
    public void startDocument() throws SAXException {
        System.out.println("开始解析web.xml文件");
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       this.qName = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        if (value == null || value.trim().length() == 0){
            return;
        }

        if (this.qName.equals("servlet-name")){
            this.servletName = value;
        } else if (this.qName.equals("servlet-class")){
            this.servletClass = value;
            servletName2ServletClass.put(this.servletName, this.servletClass);
        } else if (this.qName.equals("param-name")){
            this.paramName = value;
        } else if (this.qName.equals("param-value")){
            this.paramValue = value;
            Map<String, String> config = new HashMap<>();
            config.put(this.paramName, this.paramValue);
            servletInitConfig.put(this.servletName, config);
        } else if (this.qName.equals("url-pattern")){
            this.urlPath = value;
            servletName2UrlPath.put(this.servletName, this.urlPath);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }


}
