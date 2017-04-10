package com.nju.pams.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsFriendLetterService;
import com.nju.pams.mapper.dao.PamsFriendLetterDAO;
import com.nju.pams.model.socaility.FriendLetter;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsFriendLetterServiceImpl implements PamsFriendLetterService {

	
	@Autowired
	PamsFriendLetterDAO pamsFriendLetterDAO;

	@Override
	public List<FriendLetter> getFriendLettersBySendUserName(String sendUserName) {
		return pamsFriendLetterDAO.getFriendLettersBySendUserName(sendUserName);
	}

	@Override
	public List<FriendLetter> getFriendLettersByReceiveUserName(String receiveUserName) {
		return pamsFriendLetterDAO.getPamsLettersByReceiveUserName(receiveUserName);
	}

	@Override
	public void insertFriendLetter(FriendLetter friendLetter) {
		pamsFriendLetterDAO.insertFriendLetter(friendLetter);
	}

	@Override
	public void deleteFriendLetterByLetterId(Integer letterId) {
		pamsFriendLetterDAO.deleteFriendLetterByLetterId(letterId);
	}

	@Override
	public void setReadForFriendLetterByLetterId(Integer letterId) {
		pamsFriendLetterDAO.setReadForFriendLetterByLetterId(letterId);
	}
	
}
