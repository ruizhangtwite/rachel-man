package com.zhangrui.sort.impl;

import com.zhangrui.sort.Sort;

import java.util.stream.Stream;

/**
 * Desp: 插入排序
 * 2019-03-02 17:38
 * Created by zhangrui.
 */
public class InsertSort<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        int size = values.length;

        /**
         * 插入排序的思想：
         * 前面的数与后面的数进行比较，如果比前面的小，就插入到其中；
         * 因此，必然是从第一个开始，i = 1
         */
        for (int i = 1; i < size; i++){
            for (int j = 0; j < i; j++){
                if (values[i].compareTo(values[j]) < 0) {
                    T temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        Sort<Integer> sort = new InsertSort<>();
        Integer[] values = sort.of(32, 1, 22, 1, 13, 17, 45, 32);
        
        sort.sort(values);

        Stream.of(values).forEach(System.out::println);
    }
}
