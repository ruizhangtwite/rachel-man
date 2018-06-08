package com.zhangrui.annotation.config.configadapter;

import com.alibaba.fastjson.JSONArray;
import com.zhangrui.annotation.config.annotation.RequestList;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

/**
 * 用于解析RequestList
 * @see com.zhangrui.annotation.config.annotation.RequestList
 */
public class RequestListMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestList.class) == null ? false : true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        RequestList requestList = parameter.getParameterAnnotation(RequestList.class);

        String parameterName = parameter.getParameterName();
        String parameterVal = webRequest.getParameter(parameterName);

        Class<?> clazz = requestList.clazz(); //获取绑定的class对象
        List<?> array = JSONArray.parseArray(parameterVal, clazz);
        return array;

    }
}
