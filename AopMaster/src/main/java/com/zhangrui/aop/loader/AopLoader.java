package com.zhangrui.aop.loader;

import com.zhangrui.aop.MethodRequest;
import com.zhangrui.aop.annotation.AroundAdvice;
import com.zhangrui.aop.annotation.Aspect;
import com.zhangrui.aop.annotation.BeforeAdvice;
import com.zhangrui.aop.annotation.Pointcut;
import com.zhangrui.aop.ref.AopInvocation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Desp:
 * 2019-07-27 17:53
 * Created by zhangrui.
 */
public class AopLoader {

    private static Map<String, MethodRequest> aspectMap = new HashMap<>();
    private static Map<String, Object> instanceMap = new HashMap<>();

    public void loadAspect(String path) {
        File file = new File(path);
        String[] chlidLists = file.list();

        for (String s : chlidLists) {
            File tempFile = new File(path + File.separator + s);
            if (tempFile.isDirectory()) {
                loadAspect(path + File.separator + s);
            } else {
                hadlerClass(path + File.separator + s);
            }

        }

    }

    public void hadlerClass(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new RuntimeException("参数[fileName]为空");

        }

        int index = fileName.indexOf("classes");
        fileName = fileName.substring(index + "classes".length() + 2);

        String clazzName = fileName.replaceAll("\\\\", ".").replaceAll(".class", "");

        try {
            Class<?> clazz = Class.forName(clazzName);
            if (clazz.isInterface() || clazz.isAnnotation()) {
                return;
            }
            Aspect annotation = clazz.getAnnotation(Aspect.class);
            if (annotation != null) {
                loadAspectInstince(clazz);
            } else {
                if (clazz.getInterfaces().length > 0) {
                    AopInvocation invocation = new AopInvocation(clazz.newInstance());
                    instanceMap.put(clazz.getSimpleName(), invocation.getProxy());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadAspectInstince(Class<?> clazz) {

        String pointcutValue = "";

        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodLists = new ArrayList<>();
        for (Method method : methods) {
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            if (pointcut != null) {
                pointcutValue = pointcut.value();
            } else {
                methodLists.add(method);
            }
        }

        for (Method method : methodLists) {
            BeforeAdvice beforeAdvice = method.getAnnotation(BeforeAdvice.class);
            if (beforeAdvice != null) {
                MethodRequest request = new MethodRequest();
                request.setMethod(method);
                request.setArgs(method.getParameterTypes());

                String value = beforeAdvice.value();
                boolean matches = Pattern.matches(".+()", value);
                if (matches) {
                    value = pointcutValue;
                }

                aspectMap.put(value, request);
            }


            AroundAdvice aroundAdvice = method.getAnnotation(AroundAdvice.class);
            if (aroundAdvice != null) {

            }
        }

    }

    public static LinkedList<MethodRequest> getMethod(String methodName) {
        LinkedList<MethodRequest> methodRequests = new LinkedList<>();
        for (Map.Entry<String, MethodRequest> entry : aspectMap.entrySet()) {
            String k = entry.getKey();
            Pattern pattern = Pattern.compile(k);
            Matcher matcher = pattern.matcher(methodName);
            if (matcher.matches()) {
                methodRequests.add(entry.getValue());
            }
        }

        return methodRequests;

    }

    public static Object getProxy(String name) {
        return instanceMap.get(name);
    }

    public static boolean isMatch(String name) {
        boolean match = false;
        for (Map.Entry<String, MethodRequest> entry : aspectMap.entrySet()) {
            String k = entry.getKey();
            Pattern pattern = Pattern.compile(k);
            Matcher matcher = pattern.matcher(name);
            if (matcher.matches()) {
                match = true;
                break;
            }
        }

        return match;
    }
}
