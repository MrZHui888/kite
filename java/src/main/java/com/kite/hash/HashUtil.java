package com.kite.hash;

/**
 * @author : Guzh
 * @since : 2019-05-21
 * FNV1_32_HASH hash 算法
 *
 */
public class HashUtil {

	public static int hash(String hashStr) {

		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < hashStr.length(); i++) {
			hash = (hash ^ hashStr.charAt(i)) * p;
		}

		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;

		if (hash < 0) {
			hash = Math.abs(hash);
		}
		return hash;
	}
}
