package com.kite.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing<T> {
    private final int replicasCount;
    public final SortedMap<Long, T> circle;

    public ConsistentHashing(int replicasCount) {
        this.replicasCount = replicasCount;
        this.circle = new TreeMap();
    }

    public ConsistentHashing(int replicasCount, List<T> nodeList) {
        this.replicasCount = replicasCount;
        this.circle = new TreeMap();
        Iterator<T> var3 = nodeList.iterator();

        while (var3.hasNext()) {
            T node = var3.next();
            this.add(node);
        }

    }

    public void add(T node) {
        for (int i = 0; i < this.replicasCount; ++i) {
            this.circle.put(this.hash(node.toString() + i), node);
        }

    }

    public void remove(T node) {
        for (int i = 0; i < this.replicasCount; ++i) {
            this.circle.remove(this.hash(node.toString() + i), node);
        }

    }

    public T get(Object key) {
        if (this.circle != null && this.circle.size() > 0) {
            long hash = this.hash(String.valueOf(key));
            if (!this.circle.containsKey(hash)) {
                SortedMap<Long, T> tailMap = this.circle.tailMap(hash);
                hash = tailMap.isEmpty() ? (Long) this.circle.firstKey() : (Long) tailMap.firstKey();
            }

            return this.circle.get(hash);
        } else {
            return null;
        }
    }

    private long hash(String key) {
        return Hashing.murmur3_128().newHasher().putString(key, Charsets.UTF_8).hash().asLong();
    }


}
