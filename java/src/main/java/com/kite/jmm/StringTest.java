package com.kite.jmm;

/**
 * @author : Guzh
 * @since : 2019-07-07
 */
public class StringTest {
	public static void main(String[] args) {

		String str1 = new String("ab") + new String("c").intern();

		String str = "abc";

		String str2 = "c";

		String str3 = new String("12") + str2;

		System.out.println(str1 == str3);


		System.out.println(str == str1);

	}
}
