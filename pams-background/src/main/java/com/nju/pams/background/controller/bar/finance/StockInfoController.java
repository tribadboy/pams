package com.nju.pams.background.controller.bar.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockAPIService;
import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.biz.service.PamsStrategyService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.PamsStrategy;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.finance.StrategyElement;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.NullUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.TimeRangeUtil;

import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_FINANCE_STOCK_INFO)
public class StockInfoController {  
    
    private static final Logger logger = Logger.getLogger(StockInfoController.class);
    
    @Autowired
	PamsStockService pamsStockService;
	
	@Autowired
	PamsStockHistoryService pamsStockHistoryService;
	
	@Autowired
	PamsStockAPIService pamsStockAPIService;
	
	@Autowired
	PamsStrategyService pamsStrategyService;
 
    /**
     * 重置所有股票的最新数据和其历史数据
     * 需保证联网
     * 暂时仅考虑 沪市A股 600 601 603 开头的股票  
     * 时间为2013年至今
     * 当天数据只有在17点之后才会更新
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "updateStockData", method = RequestMethod.POST)
	public String updateStockData() {
		final JSONObject result = new JSONObject();	
		
    	logger.info("股票最新信息与历史信息重置工作开始-----------------------------------------------");
    	 	
    	List<Map<String, Object>> mapsList = pamsStockAPIService.getSpecialStockData();
		
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
							//若最新数据为当天数据，仅当时间超过17点后才加入当天数据，否则忽略
							String endDate = stockHistoryList.get(stockHistoryList.size() - 1).getSymbolDate();
							if(endDate.equals(DateUtil.getCurrentTime(DateUtil.FormatString)) 
									&& Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 17) {
								stockHistoryList.remove(stockHistoryList.size() - 1);
							}
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
   
    //返回更新股票数据页面
    @RequestMapping(value = "updateHistoryData")
    public String getUpdataHistoryDataPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	String maxDate = pamsStockHistoryService.getMaxDate();
    	
    	model.addAttribute("date", NullUtil.notNullProcess(maxDate));
        return "authc/finance-bar/updateHistoryData";
    }
    
    //返回更新策略数据页面
    @RequestMapping(value = "updateStrategyData")
    public String getUpdataStrategyDataPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	
        return "authc/finance-bar/updateStrategyData";
    }
    
    /**
     * 更新策略的综合收益
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "updateStrategyDataInfo", method = RequestMethod.POST)
	public String updateStrategyData() {
    	
    	String maxDate = pamsStockHistoryService.getMaxDate();
    	String today = DateUtil.getCurrentTime(DateUtil.FormatString);
    	if(TimeRangeUtil.getSomedayPlusDays(maxDate, 1).compareTo(today) < 0) {
    		logger.info("股票历史数据中没有昨日之后的数据，重新更新历史数据");
    		updateStockData();
    	}   	
    	
		final JSONObject result = new JSONObject();		
    	logger.info("策略信息更新工作开始-----------------------------------------------");
    	
    	pamsStrategyService.setNotStartByTodayStr(today);
    	pamsStrategyService.setOngoingByTodayStr(today);
    	pamsStrategyService.setWindUpByTodayStr(today);
    	pamsStrategyService.setClosedByTodayStr(today);
    	List<PamsStrategy> strategyList = pamsStrategyService.getPamsStrategyListByStatus(PamsStrategy.Status.Closed.getIndex());
    	if(CollectionUtils.isNotEmpty(strategyList)) {
    		for(PamsStrategy strategy : strategyList) {
    			if(null == strategy.getAvgProfit()) {
    				BigDecimal avgProfit = BigDecimal.ZERO;
    				List<StrategyElement> eleList = pamsStrategyService.getStrategyElementListByStrategyId(strategy.getStrategyId());
    				String startDate = strategy.getStartDate();
    				String endDate1 = strategy.getEndDate1();
    				String endDate2 = strategy.getEndDate2();
    				if(CollectionUtils.isNotEmpty(eleList)) {
    					for(StrategyElement element : eleList) {
    						BigDecimal percent = element.getPercent();
    						String symbolCode = element.getSymbolCode();
    						int symbolType = element.getSymbolType();
    						
    						String date1 = pamsStockHistoryService.getFloorDateByPK(symbolCode, symbolType, startDate);
    						String date2 = pamsStockHistoryService.getFloorDateByPK(symbolCode, symbolType, endDate1);
    						String date3 = pamsStockHistoryService.getFloorDateByPK(symbolCode, symbolType, endDate2);
    						if(null == date1 || null == date2 || null == date3) {
    							logger.info("无法找到该股票的历史数据-"+startDate+"-"+endDate1+"-"+endDate2);
    						} else {
    							BigDecimal originPrice = pamsStockHistoryService.getStockHistoryByPK(date1, symbolCode, symbolType).getClose();
    							List<StockHistory> hisList = pamsStockHistoryService.getPeriodStockHistoryByPK(symbolCode, symbolType,
        								date2, TimeRangeUtil.getSomedayPlusDays(date3, 1));
    							BigDecimal sum = BigDecimal.ZERO;
    							for(StockHistory his : hisList) {
    								sum = sum.add(his.getClose()).subtract(originPrice);
    							}
    							if(sum.compareTo(BigDecimal.ZERO) != 0) {
    								sum = sum.divide(originPrice.multiply(BigDecimal.valueOf(hisList.size())), 
    										4, RoundingMode.HALF_UP);
    							}
    							avgProfit = avgProfit.add(sum.multiply(percent));
    						}  						
    					}
    				}
    				pamsStrategyService.setAvgProfitByStrategyId(strategy.getStrategyId(), BigDecimalUtil.generateFormatNumber(avgProfit));
    			}
    		}
    	}
 
		logger.info("策略信息更新工作已完成------------------------------");
		ResultUtil.addSuccess(result);
		return result.toString();
	}
   
}  