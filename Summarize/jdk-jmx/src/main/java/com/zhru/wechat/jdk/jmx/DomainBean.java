package com.zhru.wechat.jdk.jmx;

import javax.management.*;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * DynamicMBean
 * @Author zhru
 * @Date 2019-10-30
 **/

public class DomainBean implements DynamicMBean {
    //属性Name
    private String name;
    //属性Age
    private int age;

    
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int add(int x, int y) {
        return x + y;
    }

    /**
     * 获取属性值
     * @param attribute
     * @return
     * @throws AttributeNotFoundException
     * @throws MBeanException
     * @throws ReflectionException
     */
    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        if (attribute == null) {
            return null;
        }
        if ("name".equals(attribute)) {
            return name;
        }
        if ("age".equals(attribute)) {
            return age;
        }
        return null;
    }

    /**
     * 设置属性值
     * @param attribute 
     * @throws AttributeNotFoundException
     * @throws InvalidAttributeValueException
     * @throws MBeanException
     * @throws ReflectionException
     */
    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        if (attribute == null) {
            return;
        }
        if ("name".equals(attribute.getName())) {
            this.name = (String) attribute.getValue();
        }
        if ("age".equals(attribute.getName())) {
            this.age = (int) attribute.getValue();
        }
    }

    /**
     * 获取Attribute集合
     * @param attributes
     * @return
     */
    @Override
    public AttributeList getAttributes(String[] attributes) {

        List<Attribute> collect = Stream.of(attributes).map(attribute -> {
            Attribute bute = null;
            try {
                Object o = getAttribute(attribute);
                bute = new Attribute(attribute, o);
            } catch (Exception e) {
                throw new RuntimeException("getAttributes()方法异常:" + e);
            }

            return bute;
        }).collect(Collectors.toList());

        return new AttributeList(collect);
    }

    /**
     * 批量设置属性
     * @param attributes
     * @return
     */
    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        List<Attribute> collect = attributes.asList().stream().map(attribute -> {
            try {
                setAttribute(attribute);
                return attribute;
            } catch (Exception e) {
                throw new RuntimeException("setAttributes()方法异常:" + e);
            }
        }).collect(Collectors.toList());
        return new AttributeList(collect);
    }

    /**
     * 设置属性时调用的方法
     * @param actionName 方法名
     * @param params 方法参数
     * @param signature 签名数组
     * @return
     * @throws MBeanException
     * @throws ReflectionException
     */
    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        return invokeMethod(actionName, params);
    }

    /**
     * MBeanInfo信息，MBeanInfo中的信息会被暴露到MBeanServer中
     * 包括属性，操作等
     * @return
     */
    @Override
    public MBeanInfo getMBeanInfo() {
        MBeanInfo mBeanInfo = null;
        try {
            mBeanInfo = new MBeanInfo(DomainBean.class.getName(), "MbeanInfo形式",
                    new MBeanAttributeInfo[]{
                            new MBeanAttributeInfo("name", String.class.getName(), "Name属性", true, true, false),
                            new MBeanAttributeInfo("age", int.class.getName(), "age属性", true, true, false)
                    }, new MBeanConstructorInfo[]{
                    new MBeanConstructorInfo("SpringUser构造器", DomainBean.class.getConstructor())

            }, new MBeanOperationInfo[]{
                    new MBeanOperationInfo("setName操作", this.getClass().getMethod("setName", String.class)),
                    new MBeanOperationInfo("getName操作", DomainBean.class.getMethod("getName")),
                    new MBeanOperationInfo("setAge操作", DomainBean.class.getMethod("setAge", int.class)),
                    new MBeanOperationInfo("getAge操作", DomainBean.class.getMethod("getAge")),
                    new MBeanOperationInfo("add操作", this.getClass().getMethod("add", int.class, int.class))
            }, new MBeanNotificationInfo[]{

            });
        } catch (Exception e) {
            throw new RuntimeException("getMBeanInfo()方法异常:" + e);
        }
        return mBeanInfo;
    }

    /**
     * 公共方法
     * @param actionName 方法名
     * @param params 方法参数
     * @return
     */
    private Object invokeMethod(String actionName, Object[] params) {
        AtomicReference<Object> obj = new AtomicReference<>();
        Method[] methods = this.getClass().getMethods();
        Arrays.stream(methods).forEach(method -> {
            if (method.getName().equals(actionName)) {
                try {
                    obj.set(method.invoke(DomainBean.this, params));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return obj.get();
    }
}
