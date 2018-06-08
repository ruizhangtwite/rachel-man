package com.zhangrui.apollo.entity;

import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * desc 实体类
 *
 * @author nsc
 * @date 2018/6/8
 */
public class AnnotationBean {

    @Value("${name:zhangrui}")
    public String name;

    @Value("${age:27}")
    public Integer age;


    /**
     * 示例：jsonBeans=[{"address":"中国大陆","tel":134767676}]
     */
    @ApolloJsonValue(value = "${jsonBeans:[]}")
    public List<MyBean> myBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<MyBean> getMyBeans() {
        return myBeans;
    }

    public void setMyBeans(List<MyBean> myBeans) {
        this.myBeans = myBeans;
    }

    @Override
    public String toString() {
        return "AnnotationBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", myBeans=" + myBeans +
                '}';
    }

    private static class MyBean{

        private String address;

        private String tel;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public String toString() {
            return "MyBean{" +
                    "address='" + address + '\'' +
                    ", tel='" + tel + '\'' +
                    '}';
        }
    }


}
