package com.yj.util.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类
 * 
 * @author Administrator
 *
 */
@Component
public class RedisClient {
	@Autowired
	private StringRedisTemplate redisTpl; // jdbcTemplate

	/**
	 * 功能描述：设置key-value到redis中
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, String value) {
		try {
			redisTpl.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能描述：根据key删除
	 * 
	 * @param key
	 * @return
	 */
	public boolean delete(String key) {
		try {
			redisTpl.delete(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能描述：通过key获取缓存里面的值
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		try {
			return redisTpl.opsForValue().get(key);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 功能描述：设置某个key过期时间
	 * 
	 * @param key
	 * @param time
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTpl.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能描述：根据key 获取过期时间
	 * 
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		return redisTpl.getExpire(key, TimeUnit.SECONDS);
	}

}
