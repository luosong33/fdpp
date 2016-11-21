package cn.newtouch.fdpp.console.cache;
import org.apache.commons.lang.StringUtils;

public class RedisLock {

	private static final char LOCK_SPLIT = '^';
	private static final String LOCK_PREFIX = "LOCK_PREFIX" + LOCK_SPLIT;

	private Redis redis;
	private String key;
	
	public RedisLock(Redis redis, String... keyParts) throws RedisLockException {
		this.redis = redis;
	    String key = LOCK_PREFIX + StringUtils.join(keyParts, LOCK_SPLIT);
	    if (redis.setnx(key, "") == 1) {
	    	this.key = key;
	    	throw new RedisLockException("key is locked!");
        }
	    redis.expire(key, 10000);
	}
	
	public void unlock() {
		if (key != null) {
			redis.del(key);
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		unlock();
		super.finalize();
	}

}
