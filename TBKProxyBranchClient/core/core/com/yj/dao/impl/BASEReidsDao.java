package com.yj.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.yj.dao.IBASEReidsDao;

/**
 * redis数据库操作
 * 
 * @author Administrator
 */
public class BASEReidsDao implements IBASEReidsDao {

	@Autowired
	protected RedisTemplate redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 保存数据
	 */
	public void saveObject(final String k, final String v) {
		try {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(k);
					byte[] value = serializer.serialize(v);
					return connection.setNX(key, value);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存数据
	 */
	public void updateObject(final String k, final String v) {
		try {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection)
						throws DataAccessException {
					RedisSerializer<String> serializer = getRedisSerializer();
					byte[] key = serializer.serialize(k);
					byte[] value = serializer.serialize(v);
					connection.set(key, value);
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 */
	public void deleteObject(final String k) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(k);
				connection.del(key);
				return true;
			}
		});
	}

	/**
	 * 查询
	 */
	public Object searchObject(final String k) {
		Object result = redisTemplate.execute(new RedisCallback() {
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(k);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				return serializer.deserialize(value);
			}
		});
		return result;
	}

}
