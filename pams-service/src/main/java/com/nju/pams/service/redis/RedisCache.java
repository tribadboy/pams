package com.nju.pams.service.redis;

import org.springframework.cache.Cache;  
import org.springframework.cache.support.SimpleValueWrapper;  
import org.springframework.dao.DataAccessException;  
import org.springframework.data.redis.connection.RedisConnection;  
import org.springframework.data.redis.core.RedisCallback;  
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.nju.pams.util.SerializeUtil;  
  
public class RedisCache implements Cache{  
      
    private RedisTemplate<String, Object> redisTemplate;    		  //redis template
    private String name;    										  //缓存名称
    
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
      System.out.println("get key \"" + keyStr + "\"  from redis cache \"" + name + "\"");  

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
    	  System.out.println("get key \"" + keyStr + "\" successfully");  
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
        System.out.println("put key \"" + keyStr + "\" into redis cache \"" + name + "\"");  
        if(!StringUtils.isEmpty(keyStr)) {
        	final Object finalValue = value;
        	final long liveTime = 86400;   	// 24 * 60 * 60 缓存时间设置为1天
        	redisTemplate.execute(new RedisCallback<Boolean>() {    
                @Override
        		public Boolean doInRedis(RedisConnection connection) throws DataAccessException {    
                     byte[] keyByte = keyStr.getBytes();    
                     byte[] valueByte = SerializeUtil.serialize(finalValue);    
                     connection.set(keyByte, valueByte);   
                     connection.expire(keyByte, liveTime);   
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
        System.out.println("del key: \"" + keyStr + "\" from redis cache \"" + name + "\"");  
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
    	 System.out.println("clear all keys from redis cache \"" + name + "\"");  
         redisTemplate.execute(new RedisCallback<String>() {    
        	 @Override
             public String doInRedis(RedisConnection connection) throws DataAccessException {    
                 connection.flushDb();    
                 return "ok";    
             }    
         });    
      }  
  
}  