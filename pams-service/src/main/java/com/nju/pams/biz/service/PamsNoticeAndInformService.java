package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.system.PamsNotice;

public interface PamsNoticeAndInformService {
	
	PamsNotice getPamsNoticeByNoticeId(Integer noticeId);
	
	PamsNotice getNewestValidPamsNotice();
	
	List<PamsNotice> getAllPamsNotice();
	
	void insertPamsNotice(PamsNotice pamsNotice);
	
	void updatePamsNotice(PamsNotice pamsNotice);
	
	void deletePamsNoticeByNoticeId(Integer noticeId);
}
