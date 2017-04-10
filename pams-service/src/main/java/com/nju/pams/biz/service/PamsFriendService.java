package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.socaility.FriendRequest;
import com.nju.pams.model.socaility.PamsFriend;

public interface PamsFriendService {
	//查询好友列表
	List<PamsFriend> getPamsFriendsByUserId(Integer userId);
	
	//删除好友
	void deletePamsFriendById(Integer id);
	
	//查询发送的好友请求列表
	List<FriendRequest> getPamsFriendsByRequestUserName(String requestUserName);
	
	//查询接收的好友请求列表
	List<FriendRequest> getPamsFriendsByResponseUserName(String responseUserName);
	
	//创建好友请求（首先检查是否是好友，且没有重复的正在请求中的好友请求）
	boolean insertFriendRequest(FriendRequest friendRequest);	
	
	//删除好友请求
	void deleteFriendRequestByRequestId(Integer requestId);
	
	//拒绝好友请求
	void setRejectForFriendRequestByRequestId(Integer requestId);
	
	//接受好友请求(首先检查双发是否是好友）
	void setAcceptForFriendRequestByRequestId(Integer requestId);
	
	//修改好友名称备注
	void updatePamsFriendMessageById(Integer id, String friendMessage);
	
}
