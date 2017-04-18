package com.nju.pams.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockAPIService;
import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_FINANCE)
public class WebFinanceController {  
	
	@Autowired
	PamsStockService pamsStockService;
	
	@Autowired
	PamsStockHistoryService pamsStockHistoryService;
	
	@Autowired
	PamsStockAPIService pamsStockAPIService;
    
    private static final Logger logger = Logger.getLogger(WebFinanceController.class);
    
    //TODO  本页面部分接口应限制管理员权限
 
    /**
     * 重置所有股票的最新数据和其历史数据
     * 需保证联网
     * 暂时仅考虑 沪市A股 600 601 603 开头的股票  
     * 时间为2013年至今
     * 数据重置时间需要保证在每天17:00之后
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "resetStockData", method = RequestMethod.GET)
	public String resetStockInfo() {
		final JSONObject result = new JSONObject();	
		
		//检查更新时间是否复合要求
    	int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    	if(currentHour < 17) {
    		logger.info("为保证当天数据不再变化，更新时间应该在下午五点之后，拒绝股票数据重置");
    		ResultUtil.addSuccess(result);
    		return result.toString();
    	}
    	
    	logger.info("股票最新信息与历史信息重置工作开始-----------------------------------------------");
		List<Map<String, Object>> mapsList = new LinkedList<Map<String, Object>>();
		//通过网易api获取股票信息
		for(int i = 600000; i < 604000; i += 1000) {
			String stockData = pamsStockAPIService.searchStockInfoBetweenStartCodeAndEndCodeUsingAPI(i, i+1000);
			if(null != stockData) {
				mapsList.addAll(pamsStockAPIService.getStocksFromStockInfo(stockData));
			}
		}
		
		if(CollectionUtils.isNotEmpty(mapsList)) {
			logger.info("从网易财经获取的最新股票数据成功，共获得股票数量：" + mapsList.size());
			List<PamsStock> stocksList = new ArrayList<PamsStock>(mapsList.size());
			for(Map<String, Object> map : mapsList) {
				stocksList.add(new PamsStock((String)map.get("symbolCode"), (int)map.get("symbolType"),
						(String)map.get("symbolName"), (int)map.get("status")));
			}
			
			//解析数据有效，将数据库中原本的所有股票信息设置为无效 
			pamsStockService.setStocksInvalidBySymbolType(PamsStock.SymbolType.SH.getIndex());
			//更新数据库中的所有的股票信息
			pamsStockService.replaceStocksList(stocksList);		

			//遍历所有有效的股票，将其历史信息更新
			int index = 0;
			for(PamsStock stock : stocksList) {
				//若该股票当前状态有效，从网易财经api获取其历史信息，并补充到数据库
				if(stock.getStatus() == PamsStock.Status.Valid.getIndex()) {
					int todayYear = Calendar.getInstance().get(Calendar.YEAR);
					for(int year = todayYear; year <= todayYear; year++) {
						String historyData = pamsStockAPIService.searchStockHistoryInCertainYearUsingAPI(stock.getSymbolCode(), year);
						if(null == historyData) {
							//该股票在这一年没有数据，跳过
							continue;
						}
						List<StockHistory> stockHistoryList = pamsStockAPIService.getStockHistorysFromStockHistoryInfo(historyData);
						if(CollectionUtils.isNotEmpty(stockHistoryList)) {
							pamsStockHistoryService.insertIgnoreStockHistoryList(stockHistoryList);
						}
					}
				}
				logger.info("股票的历史数据补充已经完成：" + ++index);
			}
		}
		logger.info("股票最新数据与历史数据重置工作已完成------------------------------");
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
  
}  