package com.zhangrui.annotation.config.configadapter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.alibaba.fastjson.JSONObject;
import com.zhangrui.annotation.config.annotation.RequestBean;

public class RequestBeanMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(RequestBean.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		RequestBean requestBean = parameter.getParameterAnnotation(RequestBean.class);
		
		String name = parameter.getParameterName();
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String value = request.getParameter(name);
		
		Object object = JSONObject.parseObject(value, requestBean.clazz());
		return object;
	}
	
}
