package com.nju.pams.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.model.User;
import com.nju.pams.service.IUserService;
import com.nju.pams.service.dao.IUserDAO;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements IUserService {
	
	//Logger logger = Logger.getLogger(AssetAllocationServiceImpl.class);
	
	@Autowired
	IUserDAO userDAO;

	@Override
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

}
