package com.nju.pams.service.redis;

import org.apache.log4j.Logger;
import org.springframework.cache.Cache;  
import org.springframework.cache.support.SimpleValueWrapper;  
import org.springframework.dao.DataAccessException;  
import org.springframework.data.redis.connection.RedisConnection;  
import org.springframework.data.redis.core.RedisCallback;  
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.nju.pams.util.SerializeUtil;  
  
public class RedisCache implements Cache{  
      
	private static final Logger logger = Logger.getLogger(RedisCache.class);  
	
    private RedisTemplate<String, Object> redisTemplate;    		  //redis template
    private String name;    										  //缓存名称
    
    /** 
     * 缓存失效时间，时间到了会自动更新 
     */  
    private long liveTime = 0;  
    
    public long getLiveTime() {  
        return liveTime;  
    }       
    public void setLiveTime(long liveTime) {  
        this.liveTime = liveTime;  
    } 
    
    public RedisTemplate<String, Object> getRedisTemplate() {  
        return redisTemplate;    
    }      
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;    
    }     
    public void setName(String name) {  
        this.name = name;    
    }     
    @Override    
    public String getName() {  
        return this.name;    
    }   
    @Override    
    public Object getNativeCache() {   
        return this.redisTemplate;    
    }  
   
    /**
     * 根据key取出缓存
     */
    @Override    
    public ValueWrapper get(Object key) {  
      if(null == key) {
    	  return null;
      }
      final String keyStr;
      if(key instanceof String) {
    	  keyStr = (String)key;
      } else {
    	  keyStr = key.toString();
      }
      logger.info("find key \"" + keyStr + "\"  from redis cache \"" + name + "\"");
      Object object = null;  
      object = redisTemplate.execute(new RedisCallback<Object>() {  
    	  @Override
    	  public Object doInRedis(RedisConnection connection) throws DataAccessException {  
    		  byte[] key = keyStr.getBytes();  
    		  byte[] value = connection.get(key);  
    		  	if (value == null) {  
    		  		return null;  
    		  	}  
    		  	return SerializeUtil.deserialize(value);  
          }  
       });  
      if(null != object) {
    	  logger.info("get key \"" + keyStr + "\" successfully");
      } else {
    	  logger.info("not get key \"" + keyStr + "\"");
      }
      return (object != null ? new SimpleValueWrapper(object) : null);  
    }  
    
    /**
     * 放入缓存对象
     */
    @Override    
    public void put(Object key, Object value) {  
    	if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
    		return;
    	}
    	final String keyStr;
    	if(key instanceof String) {
    		keyStr = (String)key;
    	} else {
    		keyStr = key.toString();
    	}
    	logger.info("put key \"" + keyStr + "\" into redis cache \"" + name + "\"");
        if(!StringUtils.isEmpty(keyStr)) {
        	final Object finalValue = value;
        	redisTemplate.execute(new RedisCallback<Boolean>() {    
                @Override
        		public Boolean doInRedis(RedisConnection connection) throws DataAccessException {    
                     byte[] keyByte = keyStr.getBytes();    
                     byte[] valueByte = SerializeUtil.serialize(finalValue);    
                     connection.set(keyByte, valueByte);   
                     if (getLiveTime() > 0) {  
                    	 //不设置liveTime即默认为永久缓存
                         connection.expire(keyByte, getLiveTime());  
                     }  
                     return true;
                } 
             });    
        }  
    }
  
    /*
     * 根据key值，删除对应的缓存
     */
    @Override    
    public void evict(Object key) {    
    	if(null == key) {
    		return;
    	}
    	final String keyStr;
    	if(key instanceof String) {
    		keyStr = (String)key;
    	} else {
    		keyStr = key.toString();
    	}
    	logger.info("del key: \"" + keyStr + "\" from redis cache \"" + name + "\"");
        if(!StringUtils.isEmpty(keyStr)) {
        	redisTemplate.execute(new RedisCallback<Long>() {    
        	     public Long doInRedis(RedisConnection connection) throws DataAccessException {    
        	         return connection.del(keyStr.getBytes());    
        	     }
        	});    
        }  
    }
       
    /*
     * 清除全部缓存
     */
     @Override    
     public void clear() {  
    	 logger.info("clear all keys from redis cache \"" + name + "\"");
         redisTemplate.execute(new RedisCallback<String>() {    
        	 @Override
             public String doInRedis(RedisConnection connection) throws DataAccessException {    
                 connection.flushDb();    
                 return "ok";    
             }    
         });    
      }  
  
}  