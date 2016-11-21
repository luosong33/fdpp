package cn.newtouch.fdpp.console.cache;

public class RedisUtils {

	private static RedisPool pool;
	
	static {
		pool = new RedisPool();
		pool.setMaxTotal(100);
		pool.init("node5", 6379);
	}
	
	public static RedisPool getPool() {
		return pool;
	}
}
