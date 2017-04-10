package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.socaility.FriendLetter;

public interface PamsFriendLetterService {
	//查询某个用户发送的所有私信
	List<FriendLetter> getFriendLettersBySendUserName(String sendUserName);
	
	//查询某个用户接收的所有私信
	List<FriendLetter> getFriendLettersByReceiveUserName(String receiveUserName);
	
	//给好友发送私信
	void insertFriendLetter(FriendLetter friendLetter);
	
	//删除某条私信
	void deleteFriendLetterByLetterId(Integer letterId);
	
	//设置某条私信的状态为已读
	void setReadForFriendLetterByLetterId(Integer letterId);
}
