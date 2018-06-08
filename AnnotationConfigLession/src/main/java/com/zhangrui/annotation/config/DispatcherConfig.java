package com.zhangrui.annotation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhangrui.annotation.config.configadapter.RequestBeanMethodArgumentResolver;
import com.zhangrui.annotation.config.configadapter.RequestListMethodArgumentResolver;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MappingJackson2XmlView;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.zhangrui.annotation")
public class DispatcherConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //JSON格式的数据输出,对所有的请求都使用
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jackson2HttpMessageConverter.setObjectMapper(mapper);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.valueOf("application/json;charset=UTF-8"));
        jackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
        converters.add(jackson2HttpMessageConverter);

        //JSON转XML格式数据输出
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter(builder.xml().build());
        converters.add(xmlConverter);

        //处理jsp页面，默认就是页面形式的
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        messageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaType.TEXT_HTML}));
        converters.add(messageConverter);
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/jsp/", ".jsp");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        RequestListMethodArgumentResolver requestListResolver = new RequestListMethodArgumentResolver();
        argumentResolvers.add(requestListResolver);

        RequestBeanMethodArgumentResolver beanArgumentResolver = new RequestBeanMethodArgumentResolver();
        argumentResolvers.add(beanArgumentResolver);
    }
}
