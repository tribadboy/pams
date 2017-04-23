package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.system.FinancialNews;

public interface PamsFinancialNewsService {
	
	FinancialNews getFinancialNewsByNewsId(Integer newsId);
	
	List<FinancialNews> getFinancialNewsList();
	
	void insertFinancialNews(FinancialNews financialNews);
	
	void deleteFinancialNewsByNewsId(Integer newsId);
}
