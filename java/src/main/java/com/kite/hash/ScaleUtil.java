package com.kite.hash;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.*;

public class ScaleUtil {

    private static final int SOLT_COUNT = 32768;
    private static final int VIRTUAL_NODES = 10;
    private static HashFunction hashFunc = Hashing.murmur3_128();

    public ScaleUtil() {
    }

    public static int getSolt(long key) {
        int hashCode = hashFunc.newHasher().putLong(key).hash().hashCode();
        hashCode = hashCode < 0 ? Math.abs(hashCode) : hashCode;
        return hashCode & 32767;
    }

    public static int getSolt(String key) {
        int hashCode = hashFunc.newHasher().putString(key, Charsets.UTF_8).hash().hashCode();
        hashCode = hashCode < 0 ? Math.abs(hashCode) : hashCode;
        return hashCode & 32767;
    }

    public static Map<Integer, List<Long>> getSoltPartition(List<Long> keys) {
        Map<Integer, List<Long>> map = new HashMap();
        List<Long> soltList = null;
        Iterator var4 = keys.iterator();

        while(var4.hasNext()) {
            Long key = (Long)var4.next();
            int solt = getSolt(key);
            soltList = (List)map.computeIfAbsent(solt, (k) -> {
                return new ArrayList();
            });
            soltList.add(key);
        }

        return map;
    }

    public static String getOwnerNode(int solt, List<String> nodes) {
        ConsistentHashing<String> consistentHas = new ConsistentHashing(10, nodes);
        return (String)consistentHas.get(solt);
    }
}
