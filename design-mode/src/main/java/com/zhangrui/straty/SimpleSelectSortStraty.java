package com.zhangrui.straty;

/**
 * 选择排序
 * @Author zr
 * @Date 2019-05-24
 **/
public class SimpleSelectSortStraty<T extends Comparable<T>> implements SortStraty<T> {

    @Override
    public void sort(T[] array) {

        for (int i = 0; i < array.length - 1; i++){

            for (int j = i; j < array.length; j++){

                if (array[i].compareTo(array[j]) > 0){
                    T temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }

    }

}
