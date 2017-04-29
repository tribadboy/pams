package com.nju.pams.cacheable.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.cacheable.service.UserService;
import com.nju.pams.mapper.dao.UserCacheDAO;
import com.nju.pams.mapper.dao.UserDAO;
import com.nju.pams.model.User;
import com.nju.pams.model.cacheable.UserCache;

import com.nju.pams.model.constant.RedisCacheConstant;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	UserCacheDAO userCacheDAO;

	@Override
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}
	
	@Override
	@Cacheable(value=RedisCacheConstant.CACHE_USER,
			   key=RedisCacheConstant.CACHE_USER_KEY + "#id"
	)
	public UserCache getUserCacheById(Integer id) {
		return userCacheDAO.getUserCacheById(id);
	}

	//部分没有数据库添加的主键的数据，若插入db前就放入redis，会找不到主键
	@Override
	@CachePut(value=RedisCacheConstant.CACHE_USER,
			  key=RedisCacheConstant.CACHE_USER_KEY + "#userCache.id")		
	public UserCache addUserCache(UserCache userCache) {
		userCacheDAO.insertUserCache(userCache);
		return userCache;
	}

	@Override
	@CacheEvict(value=RedisCacheConstant.CACHE_USER,
				key=RedisCacheConstant.CACHE_USER_KEY + "#id",
				beforeInvocation = true)
	public void deleteUserCacheById(Integer id) {
		userCacheDAO.deleteUserCacheById(id);
	}
	
	//@CacheEvict(value = { "provinceCities", "searchCity" }, allEntries = true)  

	@Override
	@CacheEvict(value=RedisCacheConstant.CACHE_USER,
				key=RedisCacheConstant.CACHE_USER_KEY + "#userCache.id",
				beforeInvocation = true)
	public void updateUserCache(UserCache userCache) {
		userCacheDAO.updateUserCache(userCache);	
	}

}
