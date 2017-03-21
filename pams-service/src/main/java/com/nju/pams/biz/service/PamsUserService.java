package com.nju.pams.biz.service;

import com.nju.pams.model.PamsUser;

public interface PamsUserService {
	PamsUser getPamsUserByUserId(Integer userId);
	
	PamsUser getPamsUserByUsername(String username);
	
	boolean insertPamsUser(PamsUser pamsUser);
	
	void updatePamsUser(PamsUser pamsUser);
	
	void deletePamsUserByUsername(String username);
}
