package com.zhangrui.design.iterator;

/**
 * Desp:
 * 2019-05-24 20:03
 * Created by zhangrui.
 */
public interface Iterator<T> {
    
    public boolean hasNext();
    
    public T next();
}
