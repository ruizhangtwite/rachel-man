package com.zhangrui.design.medium;

/**
 * Desp: 要素元体
 * 2019-05-24 19:08
 * Created by zhangrui.
 */
public interface Student {
    
    public void wantMarkFriendsWith(Student student);
    
    public String getName();
    
    public void getWhoWantToMakeFriendsWithMe(Student student);
    
}
