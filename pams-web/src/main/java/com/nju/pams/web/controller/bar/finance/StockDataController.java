package com.nju.pams.web.controller.bar.finance;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockAPIService;
import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_FINANCE_STOCK_DATA)
public class StockDataController {  
    
    private static final Logger logger = Logger.getLogger(StockDataController.class);
    
    @Autowired
    PamsStockService pamsStockService;
    
    @Autowired
    PamsStockHistoryService pamsStockHistoryService;
    
    @Autowired
    PamsStockAPIService pamsStockAPIService;
   
    //返回股票详情页面
    @RequestMapping(value = "getStockInfo")
    public String getStockInfoPage(HttpServletRequest request, Model model,
    		@RequestParam("symbolCode") String symbolCode, @RequestParam("symbolType") Integer symbolType){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
    	List<StockHistory> list = pamsStockHistoryService.getAllStockHistoryByPK(symbolCode, symbolType);
    	String minDate = pamsStockHistoryService.getMinDateByPK(symbolCode, symbolType);
    	JSONArray array = new JSONArray();
    	if(null != minDate) {
    		model.addAttribute("year", LocalDate.parse(minDate).getYear());
    		model.addAttribute("month", LocalDate.parse(minDate).getMonthOfYear()-1);
    		model.addAttribute("day", LocalDate.parse(minDate).getDayOfMonth());
    		
    		int length = list.size();
    		BigDecimal temp = BigDecimal.ZERO;
    		for(int i = 0; i < length; i++) {
    			if(minDate.equals(list.get(i).getSymbolDate())) {
    				temp = list.get(i).getClose();
    				array.add(BigDecimalUtil.generateFormatNumber(temp));  				
    			} else {
    				array.add(BigDecimalUtil.generateFormatNumber(temp));
    				i--;				
    			}
    			minDate = LocalDate.parse(minDate).plusDays(1).toString(DateUtil.FormatString);
    		}
    	} else {
    		model.addAttribute("year", 2016);
    		model.addAttribute("month", 0);
    		model.addAttribute("day", 1);
    	}
    	
    	model.addAttribute("symbolCode", symbolCode);
    	model.addAttribute("symbolType", symbolType);
    	model.addAttribute("symbolTypeName", PamsStock.SymbolType.getMsgFromIndex(symbolType));
    	model.addAttribute("symbolName", stock.getSymbolName());
    	model.addAttribute("data", array);
    	
        return "authc/finance-bar/stockInfo1";
    }
    
    //返回股票详情页面
    @RequestMapping(value = "getStockInfo2")
    public String getStockInfo2Page(HttpServletRequest request, Model model,
    		@RequestParam("symbolCode") String symbolCode, @RequestParam("symbolType") Integer symbolType){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	List<StockHistory> list = pamsStockHistoryService.getAllStockHistoryByPK(symbolCode, symbolType);
    	JSONArray array = new JSONArray();
    	if(CollectionUtils.isNotEmpty(list)) {
    		for(StockHistory history : list) {
    			JSONArray obj = new JSONArray();
    			obj.add(DateUtil.getMillionSecondsFromDate(history.getSymbolDate()));
    			obj.add(BigDecimalUtil.generateFormatNumber(history.getOpen()));
    			obj.add(BigDecimalUtil.generateFormatNumber(history.getHigh()));
    			obj.add(BigDecimalUtil.generateFormatNumber(history.getLow()));
    			obj.add(BigDecimalUtil.generateFormatNumber(history.getClose()));
    			array.add(obj);
    		}
    	}
    	
    	PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
    	model.addAttribute("symbolCode", symbolCode);
    	model.addAttribute("symbolType", symbolType);
    	model.addAttribute("symbolTypeName", PamsStock.SymbolType.getMsgFromIndex(symbolType)); 
    	model.addAttribute("symbolName", stock.getSymbolName());
    	model.addAttribute("data", array);
        return "authc/finance-bar/stockInfo2";
    }
    
    //返回股票详情页面
    @RequestMapping(value = "getStockInfo3")
    public String getStockInfo3Page(HttpServletRequest request, Model model,
    		@RequestParam("symbolCode") String symbolCode, @RequestParam("symbolType") Integer symbolType){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
    	String maxDate = pamsStockHistoryService.getMaxDateByPK(symbolCode, symbolType);
    	String minDate = pamsStockHistoryService.getMinDateByPK(symbolCode, symbolType);
    	
    	model.addAttribute("symbolCode", symbolCode);
    	model.addAttribute("symbolType", symbolType);
    	model.addAttribute("symbolTypeName", PamsStock.SymbolType.getMsgFromIndex(symbolType));
    	model.addAttribute("maxDate", maxDate);
    	model.addAttribute("minDate", minDate);
    	model.addAttribute("symbolName", stock.getSymbolName());
        return "authc/finance-bar/stockInfo3";
    }
    
    //返回股票搜索页面
    @RequestMapping(value = "searchStock")
    public String getSearchStockPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/finance-bar/searchStock";
    }
    
    //返回实时数据页面
    @RequestMapping(value = "getRealTimeData")
    public String getRealTimeDataPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	JSONArray array = new JSONArray();
    	
    	List<Map<String, Object>> mapsList = pamsStockAPIService.getSpecialStockData();
    	
		if(CollectionUtils.isNotEmpty(mapsList)) {
			for(Map<String, Object> map : mapsList) {
				if((int)map.get("status") == PamsStock.Status.Valid.getIndex()) {
					JSONObject obj = new JSONObject();
					obj.put("symbolCode", map.get("symbolCode"));
					obj.put("symbolName", map.get("symbolName"));
					obj.put("originTime", map.get("originTime"));
					obj.put("arrow", map.get("arrow"));
					obj.put("open", map.get("open"));
					obj.put("high", map.get("high"));
					obj.put("low", map.get("low"));
					obj.put("price", map.get("price"));
					obj.put("updown", map.get("updown"));
					obj.put("percent", map.get("percent"));
					obj.put("volume", map.get("volume"));
					array.add(obj);
				}			
			}
		}
    	
		model.addAttribute("dataList", array);
        return "authc/finance-bar/realTimeData";
    }
    
 
    /**
     * 自动发送的数据格式：
     *  1. start: 开始记录的起始数，如第 20 条,从0开始
     *  2. limit : 单页多少条记录
     *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
     *
     * 返回的数据格式：
     *  {
     *     "rows" : [{},{}], //数据集合
     *     "results" : 100, //记录总数
     *     "hasError" : false, //是否存在错误
     *     "error" : "" // 仅在 hasError : true 时使用
     *   }
     * 
     */
	@ResponseBody
	@RequestMapping(value = "searchStockDataInfo")
	public String searchStockDataInfo( HttpServletRequest request,
			@RequestParam(value = "stockKey", required = false) String stockKey,
   			@RequestParam("start") final Integer start,
   			@RequestParam("limit") final Integer limit
			) {
		final JSONObject result = new JSONObject();
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        result.put("rows", "[]");
   	        result.put("results", 0);
   	        result.put("hasError", true);
   	        result.put("error", "会话已断开，请重新登录");
   	        return result.toString();
    	}
    	
    	List<PamsStock> allList;
    	if(StringUtils.isEmpty(stockKey)) {
    		allList = pamsStockService.getAllPamsStocks();
    	} else {
    		try {
				stockKey = URLDecoder.decode(stockKey, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("模糊查询的关键词解码异常");
	    		SecurityUtils.getSubject().logout();
	   	        result.put("rows", "[]");
	   	        result.put("results", 0);
	   	        result.put("hasError", true);
	   	        result.put("error", "模糊查询的关键词解码异常");
	   	        return result.toString();
			}
    		allList = pamsStockService.getPamsStocksByKey(stockKey);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsStock> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsStock stock : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("symbolCode", stock.getSymbolCode());
    		json.put("symbolName", stock.getSymbolName());
    		json.put("symbolType", stock.getSymbolType());
    		json.put("type", PamsStock.SymbolType.getMsgFromIndex(stock.getSymbolType()));
    		json.put("status", PamsStock.Status.getMsgFromIndex(stock.getStatus()));
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	/**
     * 自动发送的数据格式：
     *  1. start: 开始记录的起始数，如第 20 条,从0开始
     *  2. limit : 单页多少条记录
     *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
     *
     * 返回的数据格式：
     *  {
     *     "rows" : [{},{}], //数据集合
     *     "results" : 100, //记录总数
     *     "hasError" : false, //是否存在错误
     *     "error" : "" // 仅在 hasError : true 时使用
     *   }
     * 
     */
	@ResponseBody
	@RequestMapping(value = "searchHistoryStockDataInfo")
	public String searchHistoryStockDataInfo( HttpServletRequest request,
			@RequestParam("symbolCode") final String symbolCode,
			@RequestParam("symbolType") final Integer symbolType,
			@RequestParam("startDate") final String startDate,
			@RequestParam("endDate") final String endDate,
   			@RequestParam("start") final Integer start,
   			@RequestParam("limit") final Integer limit
			) {
		final JSONObject result = new JSONObject();
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        result.put("rows", "[]");
   	        result.put("results", 0);
   	        result.put("hasError", true);
   	        result.put("error", "会话已断开，请重新登录");
   	        return result.toString();
    	}
    	
    	List<StockHistory> allList = pamsStockHistoryService.getPeriodStockHistoryByPK(symbolCode, symbolType, startDate, endDate);
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<StockHistory> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(StockHistory history : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("symbolDate", history.getSymbolDate());
    		json.put("open", BigDecimalUtil.generateFormatString(history.getOpen()));
    		json.put("close", BigDecimalUtil.generateFormatString(history.getClose()));
    		json.put("high", BigDecimalUtil.generateFormatString(history.getHigh()));
    		json.put("low", BigDecimalUtil.generateFormatString(history.getLow()));
    		json.put("risePercent", BigDecimalUtil.generateFormatString(history.getRisePercent()));
    		json.put("volume", history.getVolume());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
}  