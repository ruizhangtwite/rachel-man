package com.zhangrui.design.iterator;

/**
 * Desp:
 * 2019-05-24 20:10
 * Created by zhangrui.
 */
public class IteratorTest {

    public static void main(String[] args) {
        ClassStudent classStudent = new ClassStudent();
        Iterator<Student> iterator = classStudent.iterator();
        while (iterator.hasNext()){
            Student student = iterator.next();
            System.out.println(student);
        }
    }

    
    
}
