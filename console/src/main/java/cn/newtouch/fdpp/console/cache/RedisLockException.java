package cn.newtouch.fdpp.console.cache;

public class RedisLockException extends Exception {

	private static final long serialVersionUID = -9047642568052895943L;

	public RedisLockException(String message) {
		super(message);
	}
	
}
