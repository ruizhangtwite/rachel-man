package com.zhangrui.straty;

/**
 * 插入排序
 *
 * @Author zr
 * @Date 2019-05-24
 **/
public class InsertSortStraty<T extends Comparable<T>> implements SortStraty<T> {

    @Override
    public void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {

            for (int j = 0; j < i; j++) {

                T temp = array[j];
                if (array[i].compareTo(array[j]) < 0){
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }
    }
}
