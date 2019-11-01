package com.zhru.wechat.jdk.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * UserMBean接口实现
 *
 * @Author zhru
 * @Date 2019-10-30
 */
public class User extends NotificationBroadcasterSupport implements UserMBean {
    
    private AtomicInteger seq = new AtomicInteger();

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;

        Notification notification = new AttributeChangeNotification(
                this,
                seq.getAndIncrement(), 
                System.currentTimeMillis(), 
                String.format("User的[name]属性从%s, 更新到%s", oldName, name),
                "name", 
                name.getClass().getName(), 
                oldName,
                name);

        sendNotification(notification);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
