package com.zhangrui.sort.impl;

import com.zhangrui.sort.Sort;

import java.util.stream.Stream;

/**
 * Desp: 选择排序
 * 2019-03-02 17:29
 * Created by zhangrui.
 */
public class SelectSort<T extends Comparable<T>> implements Sort<T> {
    
    @Override
    public void sort(T[] values) {
        int size = values.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (values[i].compareTo(values[j]) > 0) {
                    T temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        Sort<Integer> bubbleSort = new SelectSort<>();
        Integer[] values = bubbleSort.of(31, 17, 4, 7, 9, 3);
        bubbleSort.sort(values);

        Stream.of(values).forEach(System.out::println);

    }
}
