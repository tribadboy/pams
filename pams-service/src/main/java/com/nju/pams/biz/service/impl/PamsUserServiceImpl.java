package com.nju.pams.biz.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.mapper.dao.PamsUserDAO;
import com.nju.pams.model.PamsUser;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsUserServiceImpl implements PamsUserService {
	
	@Autowired
	PamsUserDAO pamsUserDAO;
	
    private static final Logger logger = Logger.getLogger(PamsUserServiceImpl.class);

	@Override
	public PamsUser getPamsUserByUserId(Integer userId) {
		return pamsUserDAO.getPamsUserByUserId(userId);
	}

	@Override
	public PamsUser getPamsUserByUsername(String username) {
		return pamsUserDAO.getPamsUserByUsername(username);
	}
	
	//在controller中使用该方法时应该注意事务处理
	@Override
	public boolean insertPamsUser(PamsUser pamsUser) {
		if(null == pamsUser || null == pamsUser.getUsername()) {
			return false;
		} else {
			String username = pamsUser.getUsername();
			if(getPamsUserByUsername(username) != null) {
				logger.info("该用户名已被注册，无法继续注册！");
				return false;
			} else {
				pamsUserDAO.insertPamsUser(pamsUser);
				return true;
			}
		}
	}

	//在controller中使用该方法时应该注意事务处理
	@Override
	public void updatePamsUser(PamsUser pamsUser) {
		pamsUserDAO.updatePamsUser(pamsUser);
	}

	@Override
	public void deletePamsUserByUsername(String username) {
		pamsUserDAO.deletePamsUserByUsername(username);
	}

}
