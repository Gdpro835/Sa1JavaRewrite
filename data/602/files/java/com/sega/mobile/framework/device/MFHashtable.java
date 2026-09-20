package com.sega.mobile.framework.device;

import java.util.Hashtable;

public class MFHashtable<K, V> {
    private Hashtable<K, V> mHashtable = new Hashtable<>();

    public V get(K key) {
        return this.mHashtable.get(key);
    }

    public void put(K key, V value) {
        this.mHashtable.put(key, value);
    }

    public void remove(K key) {
        this.mHashtable.remove(key);
    }
}
