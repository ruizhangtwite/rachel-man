package com.zhangrui.straty;

/**
 * @Author zr
 * @Date 2019-05-24
 **/
public class BuddleSortStraty<T extends Comparable<T>> implements SortStraty<T> {

    @Override
    public void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                }
            }
        }
    }
}
