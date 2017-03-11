package com.nju.pams.cacheable.service;

import com.nju.pams.model.User;
import com.nju.pams.model.cacheable.UserCache;

public interface UserService {
	User getUserById(int id);
	
	UserCache getUserCacheById(Integer id);
	
	UserCache addUserCache(UserCache userCache);
	
	void deleteUserCacheById(Integer id);
	
	void updateUserCache(UserCache userCache);
}
