package com.zhru.wechat.jdk.jmx;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * 通知监听器
 * @Author zhru
 * @Date 2019-10-30
 **/
public class UserNotifyListener implements NotificationListener {

    /**
     * 通知处理方法 
     * @param notification 通知
     * @param handback 得到这个通知后处理回调的对象
     */
    @Override
    public void handleNotification(Notification notification, Object handback) {
        
        System.out.println("得到的通知：" + notification);
        
        if (handback instanceof User) {
            User user = (User) handback;
            
            // 事件操作......
            
            System.out.println("属性变化后的user对象：" + user);
        }
    }
}
