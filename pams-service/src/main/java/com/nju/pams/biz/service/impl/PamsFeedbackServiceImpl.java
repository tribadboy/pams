package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsFeedbackService;
import com.nju.pams.mapper.dao.PamsFeedbackDAO;
import com.nju.pams.model.system.Feedback;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsFeedbackServiceImpl implements PamsFeedbackService {
	
	@Autowired
	PamsFeedbackDAO pamsFeedbackDAO;

	/**
	 * 根据backId获取反馈记录
	 */
	@Override
	public Feedback getFeedBackByBackId(Integer backId) {
		return pamsFeedbackDAO.getFeedBackByBackId(backId);
	}

	/**
	 * 获取所有的未查看的反馈
	 * 非null处理
	 */
	@Override
	public List<Feedback> getUncheckedFeedbackList() {
		List<Feedback> resultList = pamsFeedbackDAO.getUncheckedFeedbackList();
		if(null == resultList) {
			return new ArrayList<Feedback>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有已查看的反馈
	 * 非null处理
	 */
	@Override
	public List<Feedback> getCheckedFeedbackList() {
		List<Feedback> resultList = pamsFeedbackDAO.getCheckedFeedbackList();
		if(null == resultList) {
			return new ArrayList<Feedback>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有的反馈
	 * 非null处理
	 */
	@Override
	public List<Feedback> getAllFeedbackList() {
		List<Feedback> resultList = pamsFeedbackDAO.getAllFeedbackList();
		if(null == resultList) {
			return new ArrayList<Feedback>();
		} else {
			return resultList;
		}
	}

	/**
	 * 插入一条反馈记录
	 */
	@Override
	public void insertLoanRecord(Feedback feedback) {
		pamsFeedbackDAO.insertLoanRecord(feedback);
	}

	/**
	 * 将一条反馈记录设置为已查看
	 */
	@Override
	public void setCheckedFeedbackByBackId(Integer backId) {
		pamsFeedbackDAO.setCheckedFeedbackByBackId(backId);
	}

	/**
	 * 删除一条反馈记录
	 */
	@Override
	public void deleteFeedbackByBackId(Integer backId) {
		pamsFeedbackDAO.deleteFeedbackByBackId(backId);
	}

}
