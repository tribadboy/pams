package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.PamsLoginInfo;

public interface PamsLoginInfoService {
	PamsLoginInfo getPamsLoginInfoByInfoId(Integer infoId);

	List<PamsLoginInfo> getPamsLoginInfoListByUserId(Integer userId);
	
	String getPamsRegisterTimeByUserId(Integer userId);
	
	void insertPamsLoginInfo(PamsLoginInfo pamsLoginInfo);
}
