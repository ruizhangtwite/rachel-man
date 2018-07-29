package com.zhangrui.mongodb.dao;

import com.zhangrui.mongodb.entity.Human;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Desp:
 * 2018-07-19 21:51
 * Created by zhangrui.
 */
public interface HumanRespository extends MongoRepository<Human, String> {
    
    public List<Human> getByName(String name);
}
