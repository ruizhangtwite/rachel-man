package com.zhru.wechat.jdk.jmx;

import javax.management.modelmbean.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建RequireModelMBean工具类
 *
 * @Author zhru
 * @Date 2019-10-30
 */
public class ModelMBeanUtils {

    /**
     * 创建{@link RequiredModelMBean}
     *
     * @param obj
     * @return
     */
    public static RequiredModelMBean createModelMBean(Object obj) {
        RequiredModelMBean mBean = null;
        try {
            mBean = new RequiredModelMBean();
            /**
             * RequiredModelMBean#setManagedResource(String mr, String mr_type)
             * @param mr Object that is the managed resource
             * @param mr_type The type of reference for the managed resource.
             * Can be: "ObjectReference", "Handle", "IOR", "EJBHandle",
             *    or "RMIReference".
             * In this implementation only "ObjectReference" is supported.
             */
            mBean.setManagedResource(obj, "ObjectReference");
            ModelMBeanInfo info = createModelMBeanInfo(obj.getClass());
            mBean.setModelMBeanInfo(info);

        } catch (Exception e) {
            throw new RuntimeException(
                    String.format("创建对象[%s]的RequireModelBean异常：" + e,
                            obj.getClass().getName()));
        }

        return mBean;
    }

    /**
     * 创建{@link ModelMBeanInfo}
     * 也就是对外暴露的方法集合
     *
     * @param clazz
     * @return
     */
    private static ModelMBeanInfo createModelMBeanInfo(Class clazz) {
        ModelMBeanOperationInfo[] modelMBeanOperationInfos = null;
        ModelMBeanAttributeInfo[] modelMBeanAttributeInfos = null;
        ModelMBeanConstructorInfo[] modelMBeanConstructorInfos = null;

        if (clazz == null) {
            throw new RuntimeException("clazz为空");
        }
        Method[] methods = clazz.getMethods();
        List<Method> methodList = Arrays.stream(methods).filter(method ->
                        !Object.class.equals(method.getDeclaringClass())
        ).collect(Collectors.toList());
        if (methodList != null && methodList.size() > 0) {
            modelMBeanOperationInfos = new ModelMBeanOperationInfo[methodList.size()];
            for (int i = 0; i < methodList.size(); i++) {
                modelMBeanOperationInfos[i] =
                        new ModelMBeanOperationInfo(
                                methodList.get(i).getName() + "方法",
                                methodList.get(i));
            }
        }


        Field[] fields = clazz.getFields();
        if (fields != null && fields.length > 0) {
            modelMBeanAttributeInfos = new ModelMBeanAttributeInfo[fields.length];
            for (int i = 0; i < fields.length; i++) {
                modelMBeanAttributeInfos[i] =
                        new ModelMBeanAttributeInfo(
                                fields[i].getName(),
                                fields[i].getType().getName(),
                                fields[i].getName() + "属性",
                                true,
                                true,
                                false);

            }
        }

        Constructor[] constructors = clazz.getConstructors();
        if (constructors != null && constructors.length > 0) {
            modelMBeanConstructorInfos = new ModelMBeanConstructorInfo[constructors.length];
            for (int i = 0; i < constructors.length; i++) {
                modelMBeanConstructorInfos[i] =
                        new ModelMBeanConstructorInfo(
                                constructors[i].getName() + "构造方法",
                                constructors[i]
                        );
            }
        }
        ModelMBeanInfo info = new ModelMBeanInfoSupport(
                clazz.getName(),
                "创建ModelMBeanInfo对象",
                modelMBeanAttributeInfos,
                modelMBeanConstructorInfos,
                modelMBeanOperationInfos,
                null);

        return info;
    }
}
