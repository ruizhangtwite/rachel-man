package com.zhangrui.sort.impl;

import com.zhangrui.sort.Sort;

import java.util.stream.Stream;

/**
 * Desp: 冒泡排序
 * 2019-03-02 16:32
 * Created by zhangrui.
 */
public class BubbleSort<T extends Comparable<T>> implements Sort<T> {
    
    
    @Override
    public void sort(T[] values) {
        int size = values.length;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - 1 - i; j++){
                if (values[j].compareTo(values[j + 1]) > 0){
                    T temp = values[j + 1];
                    values[j + 1] = values[j];
                    values[j] = temp;
                }
            }
        }
    }


    public static void main(String[] args) {
        Sort<Integer> bubbleSort = new BubbleSort<>();
        Integer[] values = bubbleSort.of(31, 17, 4, 7, 9, 3);
        bubbleSort.sort(values);

        Stream.of(values).forEach(System.out::println);
        
    }
}
