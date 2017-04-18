package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsLoginInfoService;
import com.nju.pams.mapper.dao.PamsLoginInfoDAO;
import com.nju.pams.model.PamsLoginInfo;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsLoginInfoServiceImpl implements PamsLoginInfoService {
	
	@Autowired
	PamsLoginInfoDAO pamsLoginInfoDAO;
	
    @Override
	public PamsLoginInfo getPamsLoginInfoByInfoId(Integer infoId) {
		return pamsLoginInfoDAO.getPamsLoginInfoByInfoId(infoId);
	}

	@Override
	public List<PamsLoginInfo> getPamsLoginInfoListByUserId(Integer userId) {
		List<PamsLoginInfo> resultList = pamsLoginInfoDAO.getPamsLoginInfoListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<PamsLoginInfo>();
		} else {
			return resultList;
		}
	}

	@Override
	public String getPamsRegisterTimeByUserId(Integer userId) {
		PamsLoginInfo loginInfo = pamsLoginInfoDAO.getPamsRegisterTimeByUserId(userId);
		if(null == loginInfo || null == loginInfo.getLoginTime()) {
			return "Unknown Time";
		} else {
			return loginInfo.getLoginTime();
		}
	}

	@Override
	public void insertPamsLoginInfo(PamsLoginInfo pamsLoginInfo) {
		pamsLoginInfoDAO.insertPamsLoginInfo(pamsLoginInfo);
	}


}
