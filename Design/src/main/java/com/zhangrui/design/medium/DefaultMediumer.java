package com.zhangrui.design.medium;

/**
 * Desp:
 * 2019-05-24 19:14
 * Created by zhangrui.
 */
public class DefaultMediumer implements Mediumer {


    @Override
    public void markFriends(Student from, Student to) {
        to.getWhoWantToMakeFriendsWithMe(from);
    }
}
