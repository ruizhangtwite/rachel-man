package com.zhru.wechat.jdk.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

/**
 * @Author zhru
 * @Date 2019-10-29
 **/
public class User extends NotificationBroadcasterSupport implements UserMBean {

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;

        Notification notification = new AttributeChangeNotification(this,
                1, System.currentTimeMillis(), String.format("从%s, 更新到%s", oldName, name),
                "name", name.getClass().getName(), oldName, name);

        sendNotification(notification);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
