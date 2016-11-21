package cn.newtouch.fdpp.console.cache;

import cn.newtouch.fdpp.console.utils.FSTUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/11/1.
 */
public class RedisCache {

    static Logger logger = Logger.getLogger(RedisCache.class.getName());
    public static Object get(String query) {
        Redis redis = RedisUtils.getPool().getRedis(0);
        try {
            logger.info("1:" + query);
            logger.info("2:"+DigestUtils.sha(query));
            logger.info("3:"+redis.get(DigestUtils.sha(query)));
            return FSTUtils.deserialize(redis.get(DigestUtils.sha(query)));
            // return null;
        } finally {
            redis.close();
        }
    }

    public static void set(String query, Object result, String[] tables) {
        if (tables == null || tables.length == 0) {
            return;
        }

        Redis redis = RedisUtils.getPool().getRedis(0);
        try {
            byte[] queryBytes = DigestUtils.sha(query);
            if (redis.exists(queryBytes)) {
                redis.set(queryBytes, FSTUtils.serialize(result));
                redis.expire(queryBytes, 60);
            } else {
                redis.sadd(queryBytes, FSTUtils.serialize(result));
                for (String table : tables) {
                    table = table.toUpperCase();
                    try {
                        addLock(table);
                        redis.sadd(table, query);
                    } finally {
                        releaseLock(table);
                    }
                }
            }
        } finally {
            redis.close();
        }
    }

    public static void set(String query, Object result, List<String> tables) {
        if (tables == null || tables.isEmpty()) {
            return;
        }
        set(query, result, tables.toArray(new String[tables.size()]));
    }

    public static void clear(String table) {
        table = table.toUpperCase();
        Redis redis = RedisUtils.getPool().getRedis(0);
        try {
            addLock(table);
            Set<String> querys = redis.smembers(table);
            if (querys != null) {
                for (String query : querys) {
                    redis.del(DigestUtils.sha(query));
                }
            }
        } finally {
            releaseLock(table);
            redis.close();
        }
    }

    private static final Map<String, ReentrantLock> LOCK = new HashMap<>();

    public static ReentrantLock getLocalLock(String key) {
        ReentrantLock lock = LOCK.get(key);
        if (lock == null) {
            synchronized (LOCK) {
                lock = LOCK.get(key);
                if (lock == null) {
                    lock = new ReentrantLock();
                    LOCK.put(key, lock);
                }
            }
        }
        return lock;
    }

    public static void addLocalLock(String key) {
        getLocalLock(key).lock();
    }

    public static void releaseLocalLock(String key) {
        ReentrantLock lock = getLocalLock(key);
        if (lock.isLocked()) {
            lock.unlock();
        }
    }

    public static void addRemoteLock(String key) {
        Redis redis = RedisUtils.getPool().getRedis(1);
        try {
            while (redis.setnx(key, "") != 1) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            redis.close();
        }
    }

    public static void releaseRemoteLock(String key) {
        Redis redis = RedisUtils.getPool().getRedis(1);
        try {
            redis.del(key);
        } finally {
            redis.close();
        }
    }

    public static void addLock(String key) {
        addLocalLock(key);
        addRemoteLock(key);
    }

    public static void releaseLock(String key) {
        releaseRemoteLock(key);
        releaseLocalLock(key);
    }
}
