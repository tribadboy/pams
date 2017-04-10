package com.nju.pams.biz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsFriendService;
import com.nju.pams.mapper.dao.PamsFriendDAO;
import com.nju.pams.mapper.dao.PamsFriendRequestDAO;
import com.nju.pams.model.socaility.FriendRequest;
import com.nju.pams.model.socaility.PamsFriend;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsFriendServiceImpl implements PamsFriendService {
	
	private static final Logger logger = Logger.getLogger(PamsFriendServiceImpl.class);
	
	@Autowired
	PamsFriendDAO pamsFriendDAO;
	@Autowired
	PamsFriendRequestDAO pamsFriendRequestDAO;
	
	/**
	 * 按照姓名升序排序显示用户的所有好友
	 */
	@Override
	public List<PamsFriend> getPamsFriendsByUserId(Integer userId) {
		return pamsFriendDAO.getPamsFriendsByUserId(userId);
	}
	
	/**
	 * 删除某个好友
	 */
	@Override
	public void deletePamsFriendById(Integer id) {
		pamsFriendDAO.deletePamsFriendById(id);
	}
	
	/**
	 * 查询用户的所有发送的好友请求列表
	 */
	@Override
	public List<FriendRequest> getPamsFriendsByRequestUserName(String requestUserName) {
		return pamsFriendRequestDAO.getPamsFriendsByRequestUserName(requestUserName);
	}
	
	/**
	 * 查询用户的所有接收的好友请求列表
	 */
	@Override
	public List<FriendRequest> getPamsFriendsByResponseUserName(String responseUserName) {
		return pamsFriendRequestDAO.getPamsFriendsByResponseUserName(responseUserName);
	}
	
	/**
	 * 创建好友请求，当双方已经是好友返回false，且当好友请求中存在正在请求中的重复请求时，返回false
	 */
	@Override
	public boolean insertFriendRequest(FriendRequest friendRequest) {
		if(null == friendRequest) {
			return false;
		}
		PamsFriend friend = pamsFriendDAO.getPamsFriendByUserIdAndFriendId(friendRequest.getRequestUserId(), 
				friendRequest.getResponseUserId());
		FriendRequest duplicateRequest = pamsFriendRequestDAO.getFriendRequestingByBothUsername(
				friendRequest.getRequestUserName(), friendRequest.getResponseUserName());
		if(null != friend || null != duplicateRequest) {
			logger.info("插入好友请求失败，该好友关系已经存在或者已经有正在请求中的重复好友请求");
			return false;
		}
		pamsFriendRequestDAO.insertFriendRequest(friendRequest);
		return true;
	}
	
	/**
	 * 删除好友请求
	 */
	@Override
	public void deleteFriendRequestByRequestId(Integer requestId) {
		pamsFriendRequestDAO.deleteFriendRequestByRequestId(requestId);
	}
	
	/**
	 * 拒绝好友请求
	 */
	@Override
	public void setRejectForFriendRequestByRequestId(Integer requestId) {
		pamsFriendRequestDAO.setRejectForFriendRequestByRequestId(requestId);
	}
	
	/**
	 * 接受好友请求，当双方不是好友时，添加两条好友记录，同时删除该好友请求记录
	 */
	@Override
	public void setAcceptForFriendRequestByRequestId(Integer requestId) {
		FriendRequest req = pamsFriendRequestDAO.getFriendRequestByRequestId(requestId);
		if(null == req) {
			return;
		}
		PamsFriend friend = pamsFriendDAO.getPamsFriendByUserIdAndFriendId(req.getRequestUserId(), 
				req.getResponseUserId());
		if(null != friend) {
			logger.info("该好友关系已经存在，故自动删除该请求");
			pamsFriendRequestDAO.deleteFriendRequestByRequestId(requestId);
			return;
		}
		PamsFriend friend1 = new PamsFriend(req.getRequestUserId(), req.getResponseUserId(), req.getResponseUserName());
		PamsFriend friend2 = new PamsFriend(req.getResponseUserId(), req.getRequestUserId(), req.getRequestUserName());
		pamsFriendDAO.insertPamsFriend(friend1);
		pamsFriendDAO.insertPamsFriend(friend2);	
		pamsFriendRequestDAO.deleteFriendRequestByRequestId(requestId);
	}

	/**
	 * 修改好友名称备注
	 * @param id
	 * @param friendMessage
	 */
	@Override
	public void updatePamsFriendMessageById(Integer id, String friendMessage) {
		pamsFriendDAO.updatePamsFriendMessageById(id, friendMessage);
	}
}
