package com.zhru.wechat.jdk.jmx;

import javax.management.MBeanServer;
import javax.management.NotificationFilterSupport;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

import static javax.management.AttributeChangeNotification.ATTRIBUTE_CHANGE;

/**
 * @Author zhru
 * @Date 2019-10-29
 **/
public class JmxAgent {

    public static void main(String[] args) throws Exception {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        User user = new User();
        ObjectName userObjectName = new ObjectName("jmxBean:name=jmx");

        NotificationFilterSupport notificationFilter = new NotificationFilterSupport();
        notificationFilter.enableType(ATTRIBUTE_CHANGE);
        mBeanServer.registerMBean(user, userObjectName);
        mBeanServer.addNotificationListener(userObjectName, new UserNotifyListener(),
                notificationFilter, user);

        mBeanServer.registerMBean(new DomainBean(), new ObjectName("jmxBean:name=dynamicMBean"));

        Thread.sleep(30 * 60 * 1000);
    }
}
