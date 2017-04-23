package com.nju.pams.biz.service;

import com.nju.pams.model.PamsUser;
import com.nju.pams.model.PamsUserPhoto;

public interface PamsUserService {
	PamsUser getPamsUserByUserId(Integer userId);
	
	PamsUser getPamsUserByUsername(String username);
	
	boolean insertPamsUser(PamsUser pamsUser);
	
	void updatePamsUser(PamsUser pamsUser);
	
	void deletePamsUserByUsername(String username);
	
	//用户图片的相关业务
	
	PamsUserPhoto getPamsUserPhotoByUserId(Integer userId);
	
	void insertPamsUserPhoto(PamsUserPhoto pamsUserPhoto);
	
	void updatePamsUserPhoto(PamsUserPhoto pamsUserPhoto);
}
