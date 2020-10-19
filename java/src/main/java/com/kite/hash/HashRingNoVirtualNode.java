package com.kite.hash;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

/**
 * @author : Guzh
 * @since : 2019-05-21
 * hash 环的测试
 * 集群中部署的话，应该是读多写少
 * 很容易想到二叉树去存储
 * hash 环没有虚拟节点
 */
public class HashRingNoVirtualNode {

    //机器节点
    private static List<String> groups = Lists.newArrayList();
    private static SortedMap<Integer, String> hashNodeMap = new TreeMap<>();

    private static ConsistentHashing<String> hashing = new ConsistentHashing<String>(8 * 8 * 8);

    /**
     * 机器初始化
     */
    static {

        for (int i = 0; i < 8; i++) {
            String ip = RandomIP.getRandomIp();
            int groupKey = HashUtil.hash(ip);
            System.out.println("初始化 机器: " + ip + " 分配在: " + groupKey);
            hashing.add(ip);
        }

    }

    /**
     * 计算weightStr对应在哪个group  获取机器
     *
     * @param weightStr
     * @return
     */
    private static String getServer(String weightStr) {
        int hashKey = HashUtil.hash(weightStr);
        // 获取大于hashKey 的键
        SortedMap<Integer, String> subMap = hashNodeMap.tailMap(hashKey);
        if (subMap == null || subMap.isEmpty()) {
            return hashNodeMap.get(hashNodeMap.firstKey());
        }

        return subMap.get(subMap.firstKey());
    }


    /**
     * hash 环测试节点
     * 不均匀 常见的hash 环测试
     *
     * @param args
     */
    public static void main(String[] args) {


        // 生成随机数进行测试
        Map<String, Integer> resMap = new HashMap<>();

        Map<String, Integer> resMap1 = new HashMap<>();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {

            long w = RandomUtils.nextLong(1723035544582l, 9723035544582l);
            String server1 = hashing.get(String.valueOf(w));

            if (resMap1.containsKey(server1)) {
                resMap1.put(server1, resMap1.getOrDefault(server1, 0) + 1);
            } else {
                resMap1.put(server1, 1);
            }
        }

        System.out.println("dddsadsa:" + (System.currentTimeMillis() - start));


        System.out.println("-------------------------------------");

        double total = resMap1.values().stream().mapToDouble(x -> x).sum();

        System.out.println(total);
        System.out.println("-------------------------------------");


        resMap1.forEach(
                (k, v) -> {
                    System.out.println("group " + k + ": " + v + "(" + v / 10000.d + "%)");
                }
        );
    }

}
