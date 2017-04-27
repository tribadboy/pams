package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.PamsAdminUser;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.PamsUserPhoto;

public interface PamsUserService {
	PamsUser getPamsUserByUserId(Integer userId);
	
	PamsUser getPamsUserByUsername(String username);
	
	boolean insertPamsUser(PamsUser pamsUser);
	
	void updatePamsUser(PamsUser pamsUser);
	
	void deletePamsUserByUsername(String username);
	
	List<PamsUser> getPamsUserList();
	
	List<PamsUser> getPamsUsersByKey(String key);
	
	//用户图片的相关业务-----------------------------------------------------
	
	PamsUserPhoto getPamsUserPhotoByUserId(Integer userId);
	
	void insertPamsUserPhoto(PamsUserPhoto pamsUserPhoto);
	
	void updatePamsUserPhoto(PamsUserPhoto pamsUserPhoto);
	
	//管理员相关的业务---------------------------------------------------------
	
	PamsAdminUser getPamsAdminUserByUsername(String username);
}
