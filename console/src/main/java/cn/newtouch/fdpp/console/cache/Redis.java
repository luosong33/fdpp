package cn.newtouch.fdpp.console.cache;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.BinaryClient.LIST_POSITION;

public class Redis {

	private Jedis jedis;

	public Redis(Jedis jedis) {
		this(jedis, 0);
	}

	public Redis(Jedis jedis, int index) {
		this.jedis = jedis;
		jedis.select(index);
	}

	public String set(String key, String value) {
		return jedis.set(key, value);
	}

	public String set(byte[] key, byte[] value) {
		return jedis.set(key, value);
	}

	public String set(String key, String value, String nxxx, String expx,
			long time) {
		return jedis.set(key, value, nxxx, expx, time);
	}

	public byte[] get(byte[] key) {
		return jedis.get(key);
	}

	public Object get(String key) {
		return jedis.get(key);
	}

	public String echo(String string) {
		return jedis.echo(string);
	}

	public Boolean exists(byte[] key) {
		return jedis.exists(key);
	}

	public Boolean exists(String key) {
		return jedis.exists(key);
	}

	public String type(String key) {
		return jedis.type(key);
	}

	public Long expire(byte[] key, int seconds) {
		return jedis.expire(key, seconds);
	}

	public Long expire(String key, int seconds) {
		return jedis.expire(key, seconds);
	}

	public Long pexpire(String key, long milliseconds) {
		return jedis.pexpire(key, milliseconds);
	}

	public Long expireAt(String key, long unixTime) {
		return jedis.expireAt(key, unixTime);
	}

	public Long pexpireAt(String key, long millisecondsTimestamp) {
		return jedis.pexpireAt(key, millisecondsTimestamp);
	}

	public Long ttl(String key) {
		return jedis.ttl(key);
	}

	public Boolean setbit(String key, long offset, boolean value) {
		return jedis.setbit(key, offset, value);
	}

	public Boolean setbit(String key, long offset, String value) {
		return jedis.setbit(key, offset, value);
	}

	public Boolean getbit(String key, long offset) {
		return jedis.getbit(key, offset);
	}

	public Long setrange(String key, long offset, String value) {
		return jedis.setrange(key, offset, value);
	}

	public String getrange(String key, long startOffset, long endOffset) {
		return jedis.getrange(key, startOffset, endOffset);
	}

	public String getSet(String key, String value) {
		return jedis.getSet(key, value);
	}

	public Long setnx(String key, String value) {
		return jedis.setnx(key, value);
	}

	public String setex(String key, int seconds, String value) {
		return jedis.setex(key, seconds, value);
	}

	public List<String> blpop(String arg) {
		return jedis.blpop(arg);
	}

	public List<String> blpop(int timeout, String key) {
		return jedis.blpop(timeout, key);
	}

	public List<String> brpop(String arg) {
		return jedis.brpop(arg);
	}

	public List<String> brpop(int timeout, String key) {
		return jedis.brpop(timeout, key);
	}

	public Long decrBy(String key, long integer) {
		return jedis.decrBy(key, integer);
	}

	public Long decr(String key) {
		return jedis.decr(key);
	}

	public Long incrBy(String key, long integer) {
		return jedis.incrBy(key, integer);
	}

	public Double incrByFloat(String key, double integer) {
		return jedis.incrByFloat(key, integer);
	}

	public Long incr(String key) {
		return jedis.incr(key);
	}

	public Long append(String key, String value) {
		return jedis.append(key, value);
	}

	public String substr(String key, int start, int end) {
		return jedis.substr(key, start, end);
	}

	public Long hset(String key, String field, String value) {
		return jedis.hset(key, field, value);
	}

	public String hget(String key, String field) {
		return jedis.hget(key, field);
	}

	public Long hsetnx(String key, String field, String value) {
		return jedis.hsetnx(key, field, value);
	}

	public String hmset(String key, Map<String, String> hash) {
		return jedis.hmset(key, hash);
	}

	public List<String> hmget(String key, String... fields) {
		return jedis.hmget(key, fields);
	}

	public Long hincrBy(String key, String field, long value) {
		return jedis.hincrBy(key, field, value);
	}

	public Double hincrByFloat(String key, String field, double value) {
		return jedis.hincrByFloat(key, field, value);
	}

	public Boolean hexists(String key, String field) {
		return jedis.hexists(key, field);
	}

	public Long del(byte[] key) {
		return jedis.del(key);
	}

	public Long del(String key) {
		return jedis.del(key);
	}

	public Long hdel(String key, String... fields) {
		return jedis.hdel(key, fields);
	}

	public Long hlen(String key) {
		return jedis.hlen(key);
	}

	public Set<String> hkeys(String key) {
		return jedis.hkeys(key);
	}

	public List<String> hvals(String key) {
		return jedis.hvals(key);
	}

	public Map<String, String> hgetAll(String key) {
		return jedis.hgetAll(key);
	}

	public Long rpush(String key, String... strings) {
		return jedis.rpush(key, strings);
	}

	public Long lpush(String key, String... strings) {
		return jedis.lpush(key, strings);
	}

	public Long lpushx(String key, String... string) {
		return jedis.lpushx(key, string);
	}

	public Long strlen(String key) {
		return jedis.strlen(key);
	}

	public Long move(String key, int dbIndex) {
		return jedis.move(key, dbIndex);
	}

	public Long rpushx(String key, String... string) {
		return jedis.rpushx(key, string);
	}

	public Long persist(String key) {
		return jedis.persist(key);
	}

	public Long llen(String key) {
		return jedis.llen(key);
	}

	public List<String> lrange(String key, long start, long end) {
		return jedis.lrange(key, start, end);
	}

	public String ltrim(String key, long start, long end) {
		return jedis.ltrim(key, start, end);
	}

	public String lindex(String key, long index) {
		return jedis.lindex(key, index);
	}

	public String lset(String key, long index, String value) {
		return jedis.lset(key, index, value);
	}

	public Long lrem(String key, long count, String value) {
		return jedis.lrem(key, count, value);
	}

	public String lpop(String key) {
		return jedis.lpop(key);
	}

	public String rpop(String key) {
		return jedis.rpop(key);
	}

	public Long sadd(byte[] key, byte[]... members) {
		return jedis.sadd(key, members);
	}

	public Long sadd(String key, String... members) {
		return jedis.sadd(key, members);
	}

	public Set<String> smembers(String key) {
		return jedis.smembers(key);
	}

	public Long srem(String key, String... members) {
		return jedis.srem(key, members);
	}

	public String spop(String key) {
		return jedis.spop(key);
	}

	public Set<String> spop(String key, long count) {
		return jedis.spop(key, count);
	}

	public Long scard(String key) {
		return jedis.scard(key);
	}

	public Boolean sismember(String key, String member) {
		return jedis.sismember(key, member);
	}

	public String srandmember(String key) {
		return jedis.srandmember(key);
	}

	public List<String> srandmember(String key, int count) {
		return jedis.srandmember(key, count);
	}

	public Long zadd(String key, double score, String member) {
		return jedis.zadd(key, score, member);
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return jedis.zadd(key, scoreMembers);
	}

	public Set<String> zrange(String key, long start, long end) {
		return jedis.zrange(key, start, end);
	}

	public Long zrem(String key, String... members) {
		return jedis.zrem(key, members);
	}

	public Double zincrby(String key, double score, String member) {
		return jedis.zincrby(key, score, member);
	}

	public Long zrank(String key, String member) {
		return jedis.zrank(key, member);
	}

	public Long zrevrank(String key, String member) {
		return jedis.zrevrank(key, member);
	}

	public Set<String> zrevrange(String key, long start, long end) {
		return jedis.zrevrange(key, start, end);
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		return jedis.zrangeWithScores(key, start, end);
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		return jedis.zrevrangeWithScores(key, start, end);
	}

	public Long zcard(String key) {
		return jedis.zcard(key);
	}

	public Double zscore(String key, String member) {
		return jedis.zscore(key, member);
	}

	public List<String> sort(String key) {
		return jedis.sort(key);
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		return jedis.sort(key, sortingParameters);
	}

	public Long zcount(String key, double min, double max) {
		return jedis.zcount(key, min, max);
	}

	public Long zcount(String key, String min, String max) {
		return jedis.zcount(key, min, max);
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		return jedis.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		return jedis.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, double max, double min,
			int offset, int count) {
		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		return jedis.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min) {
		return jedis.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min,
			double max, int offset, int count) {
		return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max,
			double min, int offset, int count) {
		return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		return jedis.zrangeByScore(key, min, max);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		return jedis.zrevrangeByScore(key, max, min);
	}

	public Set<String> zrangeByScore(String key, String min, String max,
			int offset, int count) {
		return jedis.zrangeByScore(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByScore(String key, String max, String min,
			int offset, int count) {
		return jedis.zrevrangeByScore(key, max, min, offset, count);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		return jedis.zrangeByScoreWithScores(key, min, max);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min) {
		return jedis.zrevrangeByScoreWithScores(key, max, min);
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min,
			String max, int offset, int count) {
		return jedis.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max,
			String min, int offset, int count) {
		return jedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public Long zremrangeByRank(String key, long start, long end) {
		return jedis.zremrangeByRank(key, start, end);
	}

	public Long zremrangeByScore(String key, double start, double end) {
		return jedis.zremrangeByScore(key, start, end);
	}

	public Long zremrangeByScore(String key, String start, String end) {
		return jedis.zremrangeByScore(key, start, end);
	}

	public Long zlexcount(String key, String min, String max) {
		return jedis.zlexcount(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min,
			String max) {
		return jedis.zrangeByLex(key, min, max);
	}

	public Set<String> zrangeByLex(String key, String min,
			String max, int offset, int count) {
		return jedis.zrangeByLex(key, min, max, offset, count);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) {
		return jedis.zrevrangeByLex(key, max, min);
	}

	public Set<String> zrevrangeByLex(String key, String max, String min,
			int offset, int count) {
		return jedis.zrevrangeByLex(key, max, min, offset, count);
	}

	public Long zremrangeByLex(String key, String min,
			String max) {
		return jedis.zremrangeByLex(key, min, max);
	}

	public Long linsert(String key, LIST_POSITION where, String pivot,
			String value) {
		return jedis.linsert(key, where, pivot, value);
	}

	public Long bitcount(String key) {
		return jedis.bitcount(key);
	}

	public Long bitcount(String key, long start, long end) {
		return jedis.bitcount(key, start, end);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Entry<String, String>> hscan(String key, int cursor) {
		return jedis.hscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<String> sscan(String key, int cursor) {
		return jedis.sscan(key, cursor);
	}

	@Deprecated
	/**
	 * This method is deprecated due to bug (scan cursor should be unsigned long)
	 * And will be removed on next major release
	 * @see https://github.com/xetorthio/jedis/issues/531 
	 */
	public ScanResult<Tuple> zscan(String key, int cursor) {
		return jedis.zscan(key, cursor);
	}

	public ScanResult<Entry<String, String>> hscan(String key,
			String cursor) {
		return jedis.hscan(key, cursor);
	}

	public ScanResult<String> sscan(String key, String cursor) {
		return jedis.sscan(key, cursor);
	}

	public ScanResult<Tuple> zscan(String key, String cursor) {
		return jedis.zscan(key, cursor);
	}

	public void close() {
		jedis.close();
	}

	public void resetState() {
		jedis.resetState();
	}

	public Long pfadd(String key, String... elements) {
		return jedis.pfadd(key, elements);
	}

	public long pfcount(String key) {
		return jedis.pfcount(key);
	}

}
