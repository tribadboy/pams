package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsNoticeAndInformService;
import com.nju.pams.mapper.dao.PamsNoticeDAO;
import com.nju.pams.model.system.PamsNotice;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsNoticeAndInformServiceImpl implements PamsNoticeAndInformService {
	
	@Autowired
	PamsNoticeDAO pamsNoticeDAO;

	/**
	 * 根据noticeId获取公告 
	 */
	@Override
	public PamsNotice getPamsNoticeByNoticeId(Integer noticeId) {
		return pamsNoticeDAO.getPamsNoticeByNoticeId(noticeId);
	}

	/**
	 * 获取最新的有效的一条公告
	 */
	@Override
	public PamsNotice getNewestValidPamsNotice() {
		return pamsNoticeDAO.getNewestValidPamsNotice();
	}

	/**
	 * 获取全部的公告
	 */
	@Override
	public List<PamsNotice> getAllPamsNotice() {
		List<PamsNotice> resultList = pamsNoticeDAO.getAllPamsNotice();
		if(null == resultList) {
			return new ArrayList<PamsNotice>();
		} else {
			return resultList;
		}
	}

	/**
	 * 插入一条的公告
	 */
	@Override
	public void insertPamsNotice(PamsNotice pamsNotice) {
		pamsNoticeDAO.insertPamsNotice(pamsNotice);
	}

	/**
	 * 更新一条公告
	 */
	@Override
	public void updatePamsNotice(PamsNotice pamsNotice) {
		pamsNoticeDAO.updatePamsNotice(pamsNotice);
	}

	/**
	 * 删除一条公告
	 */
	@Override
	public void deletePamsNoticeByNoticeId(Integer noticeId) {
		pamsNoticeDAO.deletePamsNoticeByNoticeId(noticeId);
	}
	
}
