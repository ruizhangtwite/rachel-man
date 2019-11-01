package com.zhru.jdk.beaninfo;

import com.zhru.jdk.beaninfo.bean.User;
import com.zhru.jdk.beaninfo.editor.UserPropertyEditor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试类
 * @Author zhru
 * @Date 2019-10-24
 **/
public class BeanPropertyEditorTest {

    static {
        registerPropertyEditors();
    }

    /**
     * 初始化阶段注册{@link PropertyEditor}集合
     */
    private static void registerPropertyEditors() {
        PropertyEditorCollector.put(Integer.class, UserPropertyEditor.class);
        //.....
        //可以注册多个.....
    }

    /**
     * 借助{@link PropertyEditor}　用于转换数据，并执行方法
     * @param obj　对象
     * @param valueMap　值{@link Map}
     * @throws Exception
     */
    private void invokeMethod(Object obj, Map<String, String> valueMap) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

        Arrays.stream(descriptors).forEach(propertyDescriptor -> {

            try {
                Class<?> type = propertyDescriptor.getPropertyType();
                Class<? extends PropertyEditor> propertyEditorClazz = PropertyEditorCollector.get(type);

                Method method = propertyDescriptor.getWriteMethod();
                Object value = valueMap.get(propertyDescriptor.getName());
                if (propertyEditorClazz != null) {
                    propertyDescriptor.setPropertyEditorClass(propertyEditorClazz);
                    PropertyEditor propertyEditor = propertyDescriptor.createPropertyEditor(obj);
                    propertyEditor.setAsText((String) value);
                    value = propertyEditor.getValue();
                }

                method.invoke(obj, value);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    public static void main(String[] args) throws Exception {
        BeanPropertyEditorTest beanPropertyEditorTest = new BeanPropertyEditorTest();
        User user = new User();
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhru");
        map.put("age", "19");

        beanPropertyEditorTest.invokeMethod(user, map);

        System.out.println(user);
    }


}
