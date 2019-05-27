package com.zhangrui.design.medium;

/**
 * Desp:
 * 2019-05-24 19:21
 * Created by zhangrui.
 */
public class MediumerTest {

    public static void main(String[] args) {
        Mediumer mediumer = new DefaultMediumer();
        Mike mike = new Mike(mediumer);
        Luce luce = new Luce(mediumer);
        mike.wantMarkFriendsWith(luce);
        
        luce.wantMarkFriendsWith(mike);
    }
}
