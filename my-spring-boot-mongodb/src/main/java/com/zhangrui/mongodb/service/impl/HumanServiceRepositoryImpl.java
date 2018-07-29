package com.zhangrui.mongodb.service.impl;

import com.zhangrui.mongodb.dao.HumanRespository;
import com.zhangrui.mongodb.entity.Human;
import com.zhangrui.mongodb.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Desp:
 * 2018-07-19 21:54
 * Created by zhangrui.
 */
@Component(value = "humanServiceRepository")
public class HumanServiceRepositoryImpl implements HumanService{
    
    @Autowired
    private HumanRespository respository;


    @Override
    public void saveHuman(Human user) {
        respository.save(user);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Human findByName(String name) {
        return respository.getByName(name).get(1);
    }

    @Override
    public void updateHuman(Human human) {
        
    }

    @Override
    public void deleteHumanById(String id) {
        respository.delete(id);
    }
}
