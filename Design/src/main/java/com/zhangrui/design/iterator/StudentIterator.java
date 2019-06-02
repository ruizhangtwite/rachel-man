package com.zhangrui.design.iterator;

/**
 * Desp:
 * 2019-05-24 20:04
 * Created by zhangrui.
 */
public class StudentIterator implements Iterator<Student> {
    
    private Student[] students = null;
    private int position;
    
    public StudentIterator(Student[] students){
        this.students = students;
    }

    @Override
    public boolean hasNext() {
        return position < students.length;
    }

    @Override
    public Student next() {
        return students[position++];
    }
}
