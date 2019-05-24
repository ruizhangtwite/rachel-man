package com.zhangrui.design.medium;

/**
 * Desp:
 * 2019-05-24 19:10
 * Created by zhangrui.
 */
public class Mike implements Student {
    
    public Mediumer mediumer;
    
    public Mike(Mediumer mediumer){
        this.mediumer = mediumer;
    }
    
    @Override
    public void wantMarkFriendsWith(Student student) {

        System.out.println("Mike 想跟" + student.getName() + "交朋友");
        mediumer.markFriends(this , student);
        
    }

    @Override
    public String getName() {
        return "Mike";
    }

    @Override
    public void getWhoWantToMakeFriendsWithMe(Student student) {
        System.out.println(this.getName() + "得到了" + student.getName() + "的交友信息");
    }
}
