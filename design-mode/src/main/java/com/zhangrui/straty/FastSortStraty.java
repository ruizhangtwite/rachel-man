package com.zhangrui.straty;

/**
 * 快速排序
 *
 * @Author zr
 * @Date 2019-05-24
 **/
public class FastSortStraty<T extends Comparable<T>> implements SortStraty<T> {

    @Override
    public void sort(T[] array) {
        int low = 0;
        int high = array.length - 1;

        sort(array, low, high);
    }

    private void sort(T[] array, int low, int high) {

        T t = array[high]; //以这个作为锚点

        if (low < high) {

            int position = getPosition(array, low, high, t);

            sort(array, low, position -1);
            sort(array, position + 1, high);
        }
    }

    private int getPosition(T[] array, int low, int high, T t) {
        int position = low;

        for (int i = low; i < high; i++) {
            if (array[i].compareTo(t) < 0) {
                low = low + 1;
            }
            for (int j = i; j < high; j++) {
                if (array[i].compareTo(array[j]) > 0) {
                    T temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
            }
        }

        return position;

    }
}
