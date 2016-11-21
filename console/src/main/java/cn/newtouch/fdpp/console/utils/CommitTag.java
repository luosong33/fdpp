package cn.newtouch.fdpp.console.utils;

public class CommitTag {
	
	private static final Object TAG = new Object();
	private static final ThreadLocal<Object> LOCAL = new ThreadLocal<Object>();
	
	public static boolean need() {
		return LOCAL.get() != null;
	}
	
	public static void set(Boolean need) {
		if (need) {
			LOCAL.set(TAG);
		} else {
			LOCAL.remove();
		}
	}

}
