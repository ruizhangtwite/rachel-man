package com.zhru.jdk.beaninfo;

import java.beans.PropertyEditor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zhru
 * @Date 2019-10-24
 * PropertyEditor的收集器 {@link PropertyEditor}
 **/
public class PropertyEditorCollector {

    /**
     * Map用于存储以类型为Key,对应的{@PropertyEditor}为值
     */
    private static final Map<Class, Class<? extends PropertyEditor>> PropertyDescInstanceMap
            = new ConcurrentHashMap<>();

    /**
     * 保存
     * @param clazz　需要转换的类
     * @param propertyEditor　转换所使用的{@PropertyEditor}
     */
    public static void put(Class clazz, Class<? extends PropertyEditor> propertyEditor) {
        PropertyDescInstanceMap.put(clazz, propertyEditor);
    }

    /**
     * 获取Class转换所需的{@PropertyEditor}
     * @param clazz　待转换的类
     * @return
     */
    public static Class<? extends  PropertyEditor> get(Class clazz) {
        return PropertyDescInstanceMap.get(clazz);
    }


}
