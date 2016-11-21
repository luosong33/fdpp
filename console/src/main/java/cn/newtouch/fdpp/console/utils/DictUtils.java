package cn.newtouch.fdpp.console.utils;

import java.util.HashMap;
import java.util.Map;

public class DictUtils {

	private static final ThreadLocal<Map<String, Map<String, String>>> LOCAL = new ThreadLocal<Map<String, Map<String, String>>>();

	private static Map<String, Map<String, String>> getDicts() {
		Map<String, Map<String, String>> dicts = LOCAL.get();
		if (dicts == null) {
			synchronized (LOCAL) {
				if (dicts == null) {
					dicts = new HashMap<String, Map<String, String>>();
					LOCAL.set(dicts);
				}
			}
		}
		return dicts;
	}

	public static Map<String, String> get(String type) {
		return getDicts().get(type);
	}

	public static void put(String type, Map<String, String> dict) {
		Map<String, Map<String, String>> dicts = getDicts();
		synchronized (dicts) {
			dicts.put(type, dict);
		}
	}

}
