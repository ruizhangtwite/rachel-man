package com.zhangrui.design.iterator;

/**
 * Desp:
 * 2019-05-24 20:06
 * Created by zhangrui.
 */
public class ClassStudent {

    private Student[] students;

    public ClassStudent() {
        students = new Student[10];
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName("zr" + i);
            student.setAge("18" + i);
            students[i] = student;
        }
    }
    
    public Iterator<Student> iterator(){
        return new StudentIterator(students);
    }
    
}
