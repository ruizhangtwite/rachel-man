package com.zhru.wechat.jdk.compile.processor;



import com.zhru.wechat.jdk.compile.processor.annotation.Respository;
import com.zhru.wechat.jdk.compile.processor.bean.Person;

import java.io.Serializable;

/**
 * @Author zhru
 * @Date 2019-11-04
 **/

@Respository
public class DefaultCrudRespository implements CrudRespository<Person>, Serializable {

    @Override
    public boolean create(Person user) {
        return false;
    }
}
