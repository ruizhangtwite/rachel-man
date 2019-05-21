package com.zhangrui.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Desp:
 * 2019-04-28 22:17
 * Created by zhangrui.
 */
public class LinkedMapLru<T> {
    
    private LinkedHashMap<String, T> linkedHashMap;
    private float loadFactor = 0.75f;

    public LinkedMapLru(final int size) {
        this.linkedHashMap = new LinkedHashMap<String,T>((int)Math.ceil(size / loadFactor) + 1, loadFactor, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, T> eldest) {
                return this.size() > size;
            }
        };
    }
    
    
    public T put(String key, T value){
        return this.linkedHashMap.put(key, value);
    }
    
    public T get(String key){
        return this.linkedHashMap.get(key);
    }


    public static void main(String[] args) {
        LinkedMapLru<Integer> linkedListLru = new LinkedMapLru<Integer>(2);
        linkedListLru.put("zhang", 2);
        linkedListLru.put("rui", 3);
        linkedListLru.put("rui", 4);
        linkedListLru.put("w", 5);

        System.out.println(linkedListLru.get("w"));
        System.out.println(linkedListLru.linkedHashMap.entrySet());
    }
}
