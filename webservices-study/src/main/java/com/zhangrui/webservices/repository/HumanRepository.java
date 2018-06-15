package com.zhangrui.webservices.repository;

import com.zhangrui.webservices.domain.Human;
import com.zhangrui.webservices.domain.HumanRequest;
import com.zhangrui.webservices.domain.HumanResponse;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Desp:
 * 2018-06-15 19:02
 * Created by zhangrui.
 */

@Repository
public class HumanRepository {
    
    Map<Long, Human> humans = new HashMap<>();
    
    @PostConstruct
    public void init(){
        humans.put(1L, new Human(1, "小燕子", "female", 24));
        humans.put(2L, new Human(2L, "尔康", "male", 28));
    }
    
    public HumanResponse getResponse(HumanRequest request){
        HumanResponse response = new HumanResponse();
        long id = request.getUserId();
        Human human = humans.get(id);
        response.setHuman(human);
        response.setStatus(true);
        return response;
    }
    
    
}
