package com.zhangrui.mongodb.service;

import com.zhangrui.mongodb.entity.Human;

/**
 * Desp:
 * 2018-07-19 21:09
 * Created by zhangrui.
 */
public interface HumanService {

    /**
     * 创建对象
     * @param user
     */
    public void saveHuman(Human user) ;

    /**
     * 获取总数
     * @return
     */
    public Long count();

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    public Human findByName(String name);

    /**
     * 更新对象
     * @param user
     */
    public void updateHuman(Human human);

    /**
     * 删除对象
     * @param id
     */
    public void deleteHumanById(String id) ;
}
