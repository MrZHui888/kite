package com.kite.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author : Guzh
 * @since : 2019-05-21
 * hash 环的测试
 * 集群中部署的话，应该是读多写少
 * 很容易想到二叉树去存储
 * hash 环没有虚拟节点
 *
 */
public class HashRingNoVirtualNode {

	//机器节点
	private static String[] groups = {"120.0.0.1", "120.0.0.2", "120.0.0.3", "120.0.0.4"};

	private static SortedMap<Integer, String> hashNodeMap = new TreeMap<>();

	/**
	 * 机器初始化
	 */
	static {
		for (String group : groups) {
			int groupKey = HashUtil.hash(group);
			System.out.println("初始化 机器: " + group + " 分配在: " + groupKey);
			hashNodeMap.put(groupKey, group);
		}
	}

	/**
	 * 计算weightStr对应在哪个group  获取机器
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
	 * @param args
	 */
	public static void main(String[] args) {
		// 生成随机数进行测试
		Map<String, Integer> resMap = new HashMap<>();

		for (int i = 0; i < 100000; i++) {
			Integer widgetId = (int) (Math.random() * 15);
			String server = getServer(widgetId.toString());
			if (resMap.containsKey(server)) {
				resMap.put(server, resMap.get(server) + 1);
			}
			else {
				resMap.put(server, 1);
			}
		}

		resMap.forEach(
				(k, v) -> {
					System.out.println("group " + k + ": " + v + "(" + v / 1000.0D + "%)");
				}
		);
	}

}
