package com.hexq;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcHashMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		map.put("str1", "val1");
		System.out.println(map.get("str1"));
	}

}
