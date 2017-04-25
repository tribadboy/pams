package com.nju.pams.web.controller.bar.finance;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockAPIService;
import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_FINANCE_POSITION)
public class PositionController {  
    
    private static final Logger logger = Logger.getLogger(PositionController.class);
   
    //返回持仓概览页面
    @RequestMapping(value = "getPositionOverall")
    public String getPositionOverallPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/positionOverall";
    }
   
    
    //返回交易记录页面
    @RequestMapping(value = "getTransactionRecord")
    public String getTransactionRecordPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	
        return "authc/finance-bar/transactionRecord";
    }
    
    //返回创建交易记录数据页面
    @RequestMapping(value = "makeTransaction")
    public String makeTransaction(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/makeTransaction";
    }
    
    //返回创建交易记录页面
    @RequestMapping(value = "makeTransaction2")
    public String makeTransaction2(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/makeTransaction2";
    }
    
    //添加转入与转出记录，然后自动返回添加页面
  	@RequestMapping(value = "addInflowAndOutflow", method = RequestMethod.POST)
     	public String addConsumption(Model model, HttpServletRequest request,
     			@RequestParam("changeTypeId") final Integer changeTypeId,
     			@RequestParam("tradeTime") final String tradeTime,
     			@RequestParam("total") final Double total
     			) {

  		String username = (String) request.getSession().getAttribute("username");
      	Integer userId = (Integer) request.getSession().getAttribute("userId");
      	if(null == username || null == userId) {
      		logger.info("session失效，需要用户重新登录");
      		SecurityUtils.getSubject().logout();
     	        return "error/logout";
      	}
      	
      	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
      	model.addAttribute("msg", "添加消费账目成功");
          return "authc/consumption-bar/addConsumption";
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
//	@ResponseBody
//	@RequestMapping(value = "searchStockDataInfo")
//	public String searchStockDataInfo( HttpServletRequest request,
//			@RequestParam(value = "stockKey", required = false) String stockKey,
//   			@RequestParam("start") final Integer start,
//   			@RequestParam("limit") final Integer limit
//			) {
//		final JSONObject result = new JSONObject();
//		String username = (String) request.getSession().getAttribute("username");
//    	Integer userId = (Integer) request.getSession().getAttribute("userId");
//    	if(null == username || null == userId) {
//    		logger.info("session失效，需要用户重新登录");
//    		SecurityUtils.getSubject().logout();
//   	        result.put("rows", "[]");
//   	        result.put("results", 0);
//   	        result.put("hasError", true);
//   	        result.put("error", "会话已断开，请重新登录");
//   	        return result.toString();
//    	}
//    	
//    	List<PamsStock> allList;
//    	if(StringUtils.isEmpty(stockKey)) {
//    		allList = pamsStockService.getAllPamsStocks();
//    	} else {
//    		try {
//				stockKey = URLDecoder.decode(stockKey, "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				logger.info("session失效，需要用户重新登录");
//	    		SecurityUtils.getSubject().logout();
//	   	        result.put("rows", "[]");
//	   	        result.put("results", 0);
//	   	        result.put("hasError", true);
//	   	        result.put("error", "会话已断开，请重新登录");
//	   	        return result.toString();
//			}
//    		allList = pamsStockService.getPamsStocksByKey(stockKey);
//    	}
//    	int total = allList.size();
//    	int end = (start + limit > total) ? total : start + limit;
//    	List<PamsStock> resultList = allList.subList(start, end);
//    	JSONArray array = new JSONArray();
//    	for(PamsStock stock : resultList) {
//    		JSONObject json = new JSONObject();
//    		json.put("symbolCode", stock.getSymbolCode());
//    		json.put("symbolName", stock.getSymbolName());
//    		json.put("symbolType", stock.getSymbolType());
//    		json.put("type", PamsStock.SymbolType.getMsgFromIndex(stock.getSymbolType()));
//    		json.put("status", PamsStock.Status.getMsgFromIndex(stock.getStatus()));
//    		array.add(json);
//    	}
//    	result.put("rows", array);
//	    result.put("results", total);
//	    result.put("hasError", false);
//    	
//    	return result.toString();
//	}	
//	
//	/**
//     * 自动发送的数据格式：
//     *  1. start: 开始记录的起始数，如第 20 条,从0开始
//     *  2. limit : 单页多少条记录
//     *  3. pageIndex : 第几页，同start参数重复，可以选择其中一个使用
//     *
//     * 返回的数据格式：
//     *  {
//     *     "rows" : [{},{}], //数据集合
//     *     "results" : 100, //记录总数
//     *     "hasError" : false, //是否存在错误
//     *     "error" : "" // 仅在 hasError : true 时使用
//     *   }
//     * 
//     */
//	@ResponseBody
//	@RequestMapping(value = "searchHistoryStockDataInfo")
//	public String searchHistoryStockDataInfo( HttpServletRequest request,
//			@RequestParam("symbolCode") final String symbolCode,
//			@RequestParam("symbolType") final Integer symbolType,
//			@RequestParam("startDate") final String startDate,
//			@RequestParam("endDate") final String endDate,
//   			@RequestParam("start") final Integer start,
//   			@RequestParam("limit") final Integer limit
//			) {
//		final JSONObject result = new JSONObject();
//		String username = (String) request.getSession().getAttribute("username");
//    	Integer userId = (Integer) request.getSession().getAttribute("userId");
//    	if(null == username || null == userId) {
//    		logger.info("session失效，需要用户重新登录");
//    		SecurityUtils.getSubject().logout();
//   	        result.put("rows", "[]");
//   	        result.put("results", 0);
//   	        result.put("hasError", true);
//   	        result.put("error", "会话已断开，请重新登录");
//   	        return result.toString();
//    	}
//    	
//    	List<StockHistory> allList = pamsStockHistoryService.getPeriodStockHistoryByPK(symbolCode, symbolType, startDate, endDate);
//    	
//    	int total = allList.size();
//    	int end = (start + limit > total) ? total : start + limit;
//    	List<StockHistory> resultList = allList.subList(start, end);
//    	JSONArray array = new JSONArray();
//    	for(StockHistory history : resultList) {
//    		JSONObject json = new JSONObject();
//    		json.put("symbolDate", history.getSymbolDate());
//    		json.put("open", BigDecimalUtil.generateFormatString(history.getOpen()));
//    		json.put("close", BigDecimalUtil.generateFormatString(history.getClose()));
//    		json.put("high", BigDecimalUtil.generateFormatString(history.getHigh()));
//    		json.put("low", BigDecimalUtil.generateFormatString(history.getLow()));
//    		json.put("risePercent", BigDecimalUtil.generateFormatString(history.getRisePercent()));
//    		json.put("volume", history.getVolume());
//    		array.add(json);
//    	}
//    	result.put("rows", array);
//	    result.put("results", total);
//	    result.put("hasError", false);
//    	
//    	return result.toString();
//	}	
//	
}  