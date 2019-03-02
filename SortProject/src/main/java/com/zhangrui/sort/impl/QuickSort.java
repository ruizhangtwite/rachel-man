package com.zhangrui.sort.impl;

import com.zhangrui.sort.Sort;

import java.util.stream.Stream;

/**
 * Desp: 快速排序
 * 2019-03-02 18:03
 * Created by zhangrui.
 */
public class QuickSort<T extends Comparable<T>> implements Sort<T> {

    /**
     * 快速排序思想：
     * 在数组中选择一个数字，判断这个数字在数组中的排序后的索引，然后将数组进行左右两侧的分割，
     * 左半部分再进行与主数组一样的排序方案，循环
     *
     * @param values
     */

    @Override
    public void sort(T[] values) {
        int n = values.length;

        int low = 0;
        int high = n - 1;

        sort(values, low, high);


    }

    private void sort(T[] values, int low, int high) {
        if (low < high) {

            //选择一个数当做轴
            T pKey = values[high];

            //判断这个值在排序后数组中的实际索引
            int pIndex = getIndex(values, low, high, pKey);
            sort(values, low, pIndex - 1);
            sort(values, pIndex + 1, high);
        }
    }

    private int getIndex(T[] values, int low, int high, T pKey) {
        if (low > high) {
            return high;
        }
        int index = low;
        for (int i = low; i < high; i++) {
            if (values[i].compareTo(pKey) <= 0) {
                index += 1;
            }

            for (int j = i + 1; j <= high; j++) {
                if (values[i].compareTo(values[j]) > 0) {
                    T temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }
        }
        return index;
    }

    public static void main(String[] args) {
        Sort<Integer> sort = new QuickSort<>();
        Integer[] values = sort.of(32, 1, 22, 1, 13, 17, 45, 32);

        sort.sort(values);

        Stream.of(values).forEach(System.out::println);
    }
}
