package com.zhru.wechat.jdk.jmx;

import javax.management.MBeanServer;
import javax.management.NotificationFilterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import static javax.management.AttributeChangeNotification.ATTRIBUTE_CHANGE;

/**
 * 1.定义MBeanServer
 * 2.定义{@link javax.management.ObjectName}
 * 3.注册MBean
 * @Author zhru
 * @Date 2019-10-30
 **/
public class JmxAgent {

    public static void main(String[] args) throws Exception {
        //定义MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        User user = new User();
        ObjectName userObjectName = new ObjectName("jmxBean:name=jmx");

        // 设置通知过滤器，只监听Attribuate_Change的类型
        NotificationFilterSupport notificationFilter =
                new NotificationFilterSupport();
        notificationFilter.enableType(ATTRIBUTE_CHANGE);

        //注册MBean
        mBeanServer.registerMBean(user, userObjectName);
        mBeanServer.registerMBean(new DomainBean(), 
                new ObjectName("jmxBean:name=dynamicMBean"));
        
        // 设置监听器
        mBeanServer.addNotificationListener(
                userObjectName,
                new UserNotifyListener(),
                notificationFilter, user);
        
       

        Thread.sleep(30 * 60 * 1000);
    }
}
