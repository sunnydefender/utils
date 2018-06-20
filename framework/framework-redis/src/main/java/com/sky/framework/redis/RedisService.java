package com.sky.framework.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import redis.clients.util.SafeEncoder;

@Service
public class RedisService {

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	public DataType type(String key) {
		return redisTemplate.type(key);
	}
	
	/**   List    */
	public String lindex(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}
	
	public List<String> lrange(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}
	
	public Long lsize(String key) {
		return redisTemplate.opsForList().size(key);
	}
	
	public void lset(String key, long index, String value) {
		redisTemplate.opsForList().set(key, index, value);
	}
	
	public Long lremove(String key, long index, Object value) {
		return redisTemplate.opsForList().remove(key, index, value);
	}

	public Long lpush(String key,String value){
		return redisTemplate.opsForList().leftPush(key,value);
	}
	
	/**     hash       */
	public Map<Object, Object> hEntries(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	public void hPut(String key, Object hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}
	
	public Long hIncr(String key, String field) {
		return redisTemplate.opsForHash().increment(key, field, 1);
	}
	
	public void hDelete(String key, String field) {
		redisTemplate.opsForHash().delete(key, field);
	}
	public Object hGet(String key,String field){
		return  redisTemplate.opsForHash().get(key, field);
	}
	public void hSet(String key,String field,String value){
		redisTemplate.opsForHash().put(key,field,value);
	}
	
	public void hMset(String key,Map<String,String> map){
		redisTemplate.opsForHash().putAll(key,map);
	}
	
	
	/**      set         */
	public Long sAdd(String key, String[] values) {
		return redisTemplate.opsForSet().add(key, values);
	}
	
	public Long sDiffStore(String key, String otherKey, String diffKey) {
		return redisTemplate.opsForSet().differenceAndStore(key, otherKey, diffKey);
	}
	
	public Long sInterStore(String key, String otherKey, String interKey) {
		return redisTemplate.opsForSet().intersectAndStore(key, otherKey, interKey);
	}
	
	public Long sUnionAndStore(String key, String otherKey, String unionKey) {
		return redisTemplate.opsForSet().unionAndStore(key, otherKey, unionKey);
	}

	public Long sSrem(String key,Object value){
		return redisTemplate.opsForSet().remove(key,value);
	}

	public Set<String> sMembers(String key) {
		return redisTemplate.opsForSet().members(key);
	}
	/** zset */
	public Set<TypedTuple<String>> zZRange(String key,long start,long end){
		return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
	}
	public Long zRem(String key,String field){
		return redisTemplate.opsForZSet().remove(key, field);
	}
	public Boolean zAdd(String key,String value,Double field){
		return redisTemplate.opsForZSet().add(key,value,field);
	}

	/** string */
	public void set(String key,String value){
		redisTemplate.opsForValue().set(key,value);
	}

	public String get(String key){
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * EX seconds -- Set the specified expire time, in seconds.
	 * PX milliseconds -- Set the specified expire time, in milliseconds.
	 * NX -- Only set the key if it does not already exist.
	 * XX -- Only set the key if it already exist.
	 * @param key
	 * @param value
	 * @param nxxx
	 * @param expx
	 * @param time
	 * @return
	 */
	public boolean set(String key, String value, String nxxx, String expx, long time) {
		Object result = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				Object executeReturn = connection.execute("set", new byte[][]{
						serializer.serialize(key), serializer.serialize(value),
						serializer.serialize(nxxx), serializer.serialize(expx),
						SafeEncoder.encode(String.valueOf(time))

				});
				return null == executeReturn ? false : true;
			}
		});
		return (Boolean) result == true ? true : false;
	}
	
}