package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.system.Feedback;

public interface PamsFeedbackService {
	Feedback getFeedBackByBackId(Integer backId);
	
	List<Feedback> getUncheckedFeedbackList();
	
	List<Feedback> getCheckedFeedbackList();
	
	List<Feedback> getAllFeedbackList();
	
	void insertLoanRecord(Feedback feedback);
	
	void setCheckedFeedbackByBackId(Integer backId);
	
	void deleteFeedbackByBackId(Integer backId);
	
}
