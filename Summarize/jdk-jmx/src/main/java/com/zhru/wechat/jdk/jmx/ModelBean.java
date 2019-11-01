package com.zhru.wechat.jdk.jmx;

/**
 * ModelMBean
 * @Author zhru
 * @Date 2019-10-30
 **/
public class ModelBean {
    
    private String beanName;
    
    private String beanDesc;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanDesc() {
        return beanDesc;
    }

    public void setBeanDesc(String beanDesc) {
        this.beanDesc = beanDesc;
    }

    @Override
    public String toString() {
        return "ModelBean{" +
                "beanDesc='" + beanDesc + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
