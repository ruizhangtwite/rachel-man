package com.zhangrui.straty;

import java.util.Arrays;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public interface SortStraty<T extends Comparable<T>> {

    default void show(T[] array){
        System.out.println(Arrays.toString(array));
    }

    void sort(T[] array);
}
