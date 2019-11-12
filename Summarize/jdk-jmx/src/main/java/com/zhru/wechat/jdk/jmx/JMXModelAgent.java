package com.zhru.wechat.jdk.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.modelmbean.RequiredModelMBean;
import java.lang.management.ManagementFactory;

/**
 * Desp: ModelMBean Agent
 * 2019-10-31 12:26
 * Created by zhru.
 */
public class JMXModelAgent {

    public static void main(String[] args) throws Exception {
        //定义MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        ModelBean bean = new ModelBean();
        ObjectName objName = new ObjectName("jmxBean:name=modelBean");
        
        //创建ModelMBean
        RequiredModelMBean mBean = ModelMBeanUtils.createModelMBean(bean);
        // 注册ModelMBean
        mBeanServer.registerMBean(mBean, objName);

        Thread.sleep(30 * 60 * 1000);
    }

}
