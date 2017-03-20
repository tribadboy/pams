package com.nju.pams.biz.service;

import com.nju.pams.model.PamsUser;

public interface PamsUserService {
	PamsUser getPamsUserById(Integer id);
	
	PamsUser getPamsUserByUsername(String username);
	
	boolean insertPamsUser(PamsUser pamsUser);
	
	void updatePamsUser(PamsUser pamsUser);
	
	void deletePamsUserByUsername(String username);
}
