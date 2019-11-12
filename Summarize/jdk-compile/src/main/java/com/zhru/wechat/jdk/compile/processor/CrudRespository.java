package com.zhru.wechat.jdk.compile.processor;

/**
 * @Author zhru
 * @Date 2019-11-04
 **/
public interface CrudRespository<T> {

    boolean create(T t);
}
