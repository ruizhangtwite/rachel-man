package com.zhangrui.mongodb;

import com.zhangrui.mongodb.entity.Human;
import com.zhangrui.mongodb.service.HumanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MySpringBootMongodbApplication.class)
public class MySpringBootMongodbApplicationTests {

    @Autowired
    @Qualifier("humanService")
    //@Qualifier("humanServiceRepository")
    private HumanService service;

    @Test
    public void testSaveHuman() throws Exception {
        Human user = new Human();
        user.setId("1234abcd11");
        user.setName("小明");
        service.saveHuman(user);
    }

    @Test
    public void testCountHuman() throws Exception {

        System.out.println(service.count());
    }

    @Test
    public void findUserByUserName() {
        Human user = service.findByName("小明");
        System.out.println("user is " + user);
    }

    @Test
    public void updateUser() {
        Human user = new Human();
        user.setId("1234abcd");
        user.setName("天空");
        
        service.updateHuman(user);
    }

    @Test
    public void deleteUserById() {
        service.deleteHumanById("1234abcd11");
    }

}
