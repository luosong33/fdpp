package cn.newtouch.fdpp.console.cache;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;


public class RedisPool {
	
	private JedisPool pool;
	private JedisPoolConfig config = new JedisPoolConfig();
	
	public void setMaxTotal(int maxTotal) {
		config.setMaxWaitMillis(1000);
		config.setMaxTotal(maxTotal);
	}
	
	public void init(String host, int port) {
	    pool = new JedisPool(config, host, port);
	}
	
	public Redis getRedis() {
		return new Redis(pool.getResource());
	}

	public Redis getRedis(int index) {
		return new Redis(pool.getResource(), index);
	}
	
	public void close() {
		pool.destroy(); 
	}

}
