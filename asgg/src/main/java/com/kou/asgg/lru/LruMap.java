package com.kou.asgg.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * lru 算法实现
 */
public class LruMap<k,v> extends LinkedHashMap<k,v> {

    private int capacity; //缓存坑位

    public LruMap(int capacity){
        super(capacity,0.75F,true);
        this.capacity=capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<k, v> eldest) {
        return super.size() > capacity;
    }

    public static void main(String[] args) {
        LruMap<Object, Object> lruMap = new LruMap<>(3);

        lruMap.put(1,"a");
        lruMap.put(2,"b");
        lruMap.put(3,"c");
        System.out.println(lruMap.keySet());

        lruMap.put(4,"d");
        System.out.println(lruMap.keySet());
    }

}
