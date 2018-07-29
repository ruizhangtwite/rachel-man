package com.zhangrui.mongodb.service.impl;

import com.zhangrui.mongodb.entity.Human;
import com.zhangrui.mongodb.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * Desp:
 * 2018-07-19 21:11
 * Created by zhangrui.
 */
@Component(value = "humanService")
public class HumanServiceImpl implements HumanService {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public void saveHuman(Human user) {
        mongoTemplate.save(user);
    }

    @Override
    public Long count() {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("name").is("小明"), Criteria.where("name").is("菜鸟教程"));
        Query query = new Query();
        query.fields().include("name").include("_id");
        query.addCriteria(criteria);
        return mongoTemplate.count(query, Human.class);
    }

    @Override
    public Human findByName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        Human user =  mongoTemplate.findOne(query , Human.class);
        return user;
    }

    @Override
    public void updateHuman(Human human) {
        Query query = new Query(Criteria.where("id").is(human.getId()));
        Update update = new Update().set("name", human.getName());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,Human.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,Human.class);
    }

    @Override
    public void deleteHumanById(String id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Human.class);
    }
}
