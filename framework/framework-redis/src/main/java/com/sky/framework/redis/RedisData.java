package com.sky.framework.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisData {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	private Map<String, Long> refreshMap = new HashMap<String, Long> ();
	
	private Map<String, String> cacheMap = new HashMap<String, String> ();
	
	/** 更新时间间隔(分钟) */
	private int refreshInterval = 5;
	
	private static final String baseDataRedisKey = "sky.ico.basedata";
	
	/**
	 * 获取基础数据
	 *   redis hash
	 * @param field
	 * @return
	 */
	public String getBaseData(String field) {
		String key = baseDataRedisKey + "." + field;
		Long lastestRefreshTimeMills = refreshMap.get(key);
		
		// 检查时间点
		long checkpointDate = DateUtils.addMinutes(new Date(), -refreshInterval).getTime();
		
		if (lastestRefreshTimeMills == null || checkpointDate >= lastestRefreshTimeMills) {
			// 如果超过refreshInterval，则重新从redis获取数据
			String value = (String) redisTemplate.opsForHash().get(baseDataRedisKey, field);
			
			if (value == null) {
				value = "";
			}
			cacheMap.put(key, value);
			refreshMap.put(key, System.currentTimeMillis());
		}
		
		return cacheMap.get(key);
	}
	
	/**
	 * 获取数据
	 * @param field
	 * @param defaultValue
	 * @return
	 */
	public String getBaseData(String field, String defaultValue) {
		String value = this.getBaseData(field);
		
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
		}
		
		return value;
	}
	
	public String getBaseDataNoCached(String field) {
		String result = (String) redisTemplate.opsForHash().get(baseDataRedisKey, field);
		
		return result == null ? "" : result;
	}
	
	public String getBaseDataNoCached(String field, String defaultValue) {
		String result = getBaseDataNoCached(field);
		if (StringUtils.isEmpty(result)) {
			return defaultValue;
		}
		
		return result;
	}
	
	/**
	 * 设置基础数据
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean setBaseData(String field, String value) {
		redisTemplate.opsForHash().put(baseDataRedisKey, field, value);
		
		return true;
	}
}
