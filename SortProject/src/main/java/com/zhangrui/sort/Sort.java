package com.zhangrui.sort;

/**
 * Desp: 排序接口
 * 2019-03-02 16:29
 * Created by zhangrui.
 */
public interface Sort<T extends Comparable<T>>  {
    
    void sort(T[] values);
    
    default <T> T[] of(T... values){
        return values;
    }
 }
