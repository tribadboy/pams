package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsFinancialNewsService;
import com.nju.pams.mapper.dao.PamsFinancialNewsDAO;
import com.nju.pams.model.system.FinancialNews;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsFinancialNewsServiceImpl implements PamsFinancialNewsService {
	
	@Autowired
	PamsFinancialNewsDAO pamsFinancialNewsDAO;

	/**
	 * 根据newsId获取新闻对象
	 */
	@Override
	public FinancialNews getFinancialNewsByNewsId(Integer newsId) {
		return pamsFinancialNewsDAO.getFinancialNewsByNewsId(newsId);
	}
	
	/**
	 * 获取全部新闻，按照时间降序排列
	 * 返回值非null
	 */
	@Override
	public List<FinancialNews> getFinancialNewsList() {
		List<FinancialNews> resultList = pamsFinancialNewsDAO.getFinancialNewsList();
		if(null == resultList) {
			return new ArrayList<FinancialNews>();
		} else {
			return resultList;
		}
	}

	/**
	 * 插入一条新闻对象
	 */
	@Override
	public void insertFinancialNews(FinancialNews financialNews) {
		pamsFinancialNewsDAO.insertFinancialNews(financialNews);
	}

	/**
	 * 删除一条新闻对象
	 */
	@Override
	public void deleteFinancialNewsByNewsId(Integer newsId) {
		pamsFinancialNewsDAO.deleteFinancialNewsByNewsId(newsId);
	}

	/**
	 * 获取某个时间段内的所有新闻
	 */
	@Override
	public List<FinancialNews> getFinancialNewsListInPeriod(String startDate, String endDate) {
		List<FinancialNews> resultList = pamsFinancialNewsDAO.getFinancialNewsListInPeriod(startDate, endDate);
		if(null == resultList) {
			return new ArrayList<FinancialNews>();
		} else {
			return resultList;
		}
	}

	/**
	 * 设置某条新闻的图片
	 */
	@Override
	public void setPictureNameByNewsId(String pictureName, Integer newsId) {
		pamsFinancialNewsDAO.setPictureNameByNewsId(pictureName, newsId);	
	}


	
}
