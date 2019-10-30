package com.zhru.wechat.jdk.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * @Author zhru
 * @Date 2019-10-29
 **/
public class UserNotifyListener implements NotificationListener {

    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println(notification);
        if (handback instanceof User) {
            User user = (User) handback;
            System.err.println(user);
        }
    }
}
