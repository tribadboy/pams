package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.system.InformUserRef;
import com.nju.pams.model.system.PamsInform;
import com.nju.pams.model.system.PamsNotice;

public interface PamsNoticeAndInformService {
	//公告的相关业务逻辑------------------------------------
	
	PamsNotice getPamsNoticeByNoticeId(Integer noticeId);
	
	PamsNotice getNewestValidPamsNotice();
	
	List<PamsNotice> getAllPamsNotice();
	
	void insertPamsNotice(PamsNotice pamsNotice);
	
	void updatePamsNotice(PamsNotice pamsNotice);
	
	void deletePamsNoticeByNoticeId(Integer noticeId);
	
	List<PamsNotice> getAllValidPamsNotice();
	
	//通知的相关业务逻辑---------------------------------------
	
	//将某个通知设置为无效状态
	void setInformInvalid(Integer informId);
	
	//将某个通知设置为有效状态
	void setInformValid(Integer informId);
	
	//将某个通知设置为针对全体用户
	void setInformTypeAll(Integer informId);
	
	//将某个通知设置为针对部分用户
	void setInformTypeSpecial(Integer informId);
	
	List<PamsInform> getAllPamsInforms();
	
	List<PamsInform> getValidPamsInforms();
	
	List<PamsInform> getValidTypeSpecialPamsInforms();
	
	List<PamsInform> getValidTypeTotalPamsInforms();
	
	List<PamsInform> getAllTypeTotalPamsInforms();
	
	List<PamsInform> getAllTypeSpecialPamsInforms();
	
	void insertInform(PamsInform pamsInform);
	
	void deletePamsInformByInformId(Integer informId);
	
	void insertInformUserRef(InformUserRef informUserRef);
	
	void deleteInformUserRefByRefId(Integer refId);
	
	List<PamsInform> getAllValidInformForCertainUser(Integer userId);
	
	InformUserRef getInformUserRefByInformIdAndUserId(Integer informId, Integer userId);
	
}
