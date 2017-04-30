package com.nju.pams.web.controller.bar.finance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.biz.service.PamsStrategyService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.finance.PamsStrategy;
import com.nju.pams.finance.StrategyElement;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.TimeRangeUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_FINANCE_STRATEGY)
public class StrategyController {  
    
    private static final Logger logger = Logger.getLogger(StrategyController.class);
    
    @Autowired
    PamsStockService pamsStockService;
    
    @Autowired
    PamsStrategyService pamsStrategyService;
   
    
    //返回创建策略页面
    @RequestMapping(value = "makeStrategy")
    public String getMakeStrategyPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/finance-bar/makeStrategy";
    }
    
    //返回编辑我的策略
    @RequestMapping(value = "checkMyStrategy")
    public String getCheckMyStrategyPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/finance-bar/checkMyStrategy";
    }
    
  
    //添加策略，然后自动返回添加页面
  	@RequestMapping(value = "add", method = RequestMethod.POST)
    public String addStrategy(Model model, HttpServletRequest request,
     			@RequestParam("strategyName") final String strategyName,
     			@RequestParam("strategyType") final Integer strategyType,
     			@RequestParam("startDate") final String startDate,
     			@RequestParam("message") final String message,
     			@RequestParam("element") final String element
     			) {

  		String username = (String) request.getSession().getAttribute("username");
      	Integer userId = (Integer) request.getSession().getAttribute("userId");
      	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
      	if(null == username || null == userId) {
      		logger.info("session失效，需要用户重新登录");
      		SecurityUtils.getSubject().logout();
     	        return "error/logout";
      	}
      	
      	JSONArray array = JSONArray.fromObject(element);
      	List<StrategyElement> eleList = new ArrayList<StrategyElement>();
      	BigDecimal sum = BigDecimal.ZERO;
      	if(CollectionUtils.isEmpty(array)) {
      		model.addAttribute("msg", "创建策略失败，您的策略中没有包涵任何股票投资");
      		return "authc/finance-bar/makeStrategy";
      	} else if(array.size() > PamsStrategy.MAX_ELEMENT_IN_STRATEGY) {
      		model.addAttribute("msg", "创建策略失败，您的策略中包涵的股票数量过多");
      		return "authc/finance-bar/makeStrategy";
      	} else {  		
      		for(int i = 0; i < array.size(); i++) {
      			JSONObject obj = array.getJSONObject(i);
      			if(null != obj) {
      				String symbolCode = obj.getString("symbolCode");
          			int symbolType = obj.getInt("symbolType");
          			PamsStock stock = pamsStockService.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
          			if(null == stock || stock.getStatus() == PamsStock.Status.Invalid.getIndex()) {
          				model.addAttribute("msg", "创建策略失败，您选择的股票不存在或者已经失效：" + symbolCode);
          	      		return "authc/finance-bar/makeStrategy";
          			}
          			BigDecimal percent = BigDecimalUtil.generateFormatNumber(obj.getDouble("percent"));
          			sum = sum.add(percent);
          			eleList.add(new StrategyElement(symbolCode, symbolType, percent));         			
      			} else {
      				model.addAttribute("msg", "创建策略失败，您投资的股票中包涵空值");
      	      		return "authc/finance-bar/makeStrategy";
      			}    			
      		}
      	}
          	
      	if(sum.compareTo(BigDecimal.valueOf(100)) > 0) {
      		model.addAttribute("msg", "创建策略失败，您投资的股票比例总和超过100%");
	      	return "authc/finance-bar/makeStrategy";
      	}
      	
      	List<PamsStrategy> hasList = pamsStrategyService.getPamsStrategyListByUserId(userId);
      	if(CollectionUtils.isNotEmpty(hasList)) {
      		if(hasList.size() > PamsStrategy.MAX_STRATEGY_NUM) {
      			model.addAttribute("msg", "创建策略失败，您创建的策略数目已经超出上限");
    	      	return "authc/finance-bar/makeStrategy";
      		}
      	}
      	
      	String endDate1 = TimeRangeUtil.getSomedayPlusDays(startDate, PamsStrategy.Type.getTypeFromIndex(strategyType).getPeriod1());
      	String endDate2 = TimeRangeUtil.getSomedayPlusDays(startDate, PamsStrategy.Type.getTypeFromIndex(strategyType).getPeriod2());
      	PamsStrategy pamsStrategy = new PamsStrategy(strategyName, userId, username, strategyType, 
        		startDate, endDate1, endDate2, message);
      	pamsStrategyService.insertPamsStrategy(pamsStrategy, eleList);
      	model.addAttribute("msg", "创建策略成功");
        return "authc/finance-bar/makeStrategy";
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
	@RequestMapping(value = "searchMyStrategyInfo")
	public String searchMyStrategyInfo( HttpServletRequest request,
			@RequestParam("strategyType") final Integer strategyType,
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
    	
    	List<PamsStrategy> allList = null;
    	if(0 == strategyType) {
    		allList = pamsStrategyService.getPamsStrategyListByUserId(userId);
    	} else {
    		allList = pamsStrategyService.getPamsStrategyListByUserIdAndStrategyType(userId, strategyType);
    	}
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsStrategy> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsStrategy strategy : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("strategyId", strategy.getStrategyId());
    		json.put("startDate", strategy.getStartDate());
    		json.put("strategyName", strategy.getStrategyName());
    		json.put("statusName", PamsStrategy.Status.getMsgFromIndex(strategy.getStatus()));
    		json.put("strategyType", PamsStrategy.Type.getMsgFromIndex(strategy.getStrategyType()));
    		json.put("message", strategy.getMessage());
    		BigDecimal avgProfit = strategy.getAvgProfit();
    		if(null != avgProfit) {
    			json.put("avgProfit", avgProfit);
    		} else {
    			json.put("avgProfit", "--");
    		}
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除某个投资策略
    @ResponseBody
	@RequestMapping(value = "deleteStrategyInfo", method = RequestMethod.POST)
	public String deleteStrategyInfo(HttpServletRequest request,
			@RequestParam(value="strategyId") final Integer strategyId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	pamsStrategyService.deletePamsStrategyByStrategyId(strategyId);
    	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    //返回策略详情
    @RequestMapping(value = "getStrategyInfo")
    public String getStrategyInfo(HttpServletRequest request, Model model,
    		@RequestParam(value="strategyId") final Integer strategyId
    		){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	PamsStrategy strategy = pamsStrategyService.getPamsStrategyByStrategyId(strategyId);
    	if(null != strategy) {
    		model.addAttribute("strategyName", strategy.getStrategyName());
    		model.addAttribute("statusName", PamsStrategy.Status.getMsgFromIndex(strategy.getStatus()));
    		model.addAttribute("strategyType", PamsStrategy.Type.getMsgFromIndex(strategy.getStrategyType()));
    		model.addAttribute("startDate", strategy.getStartDate());
    		model.addAttribute("message", strategy.getMessage());
    		if(null == strategy.getAvgProfit()) {
    			model.addAttribute("avgProfit", "--");
    		} else {
    			model.addAttribute("avgProfit", strategy.getAvgProfit());
    		}
    		List<StrategyElement> elementList = pamsStrategyService.getStrategyElementListByStrategyId(strategyId);
    		if(CollectionUtils.isNotEmpty(elementList)) {
    			JSONArray array = new JSONArray();
    			for(StrategyElement e : elementList) {
    				JSONObject obj = new JSONObject();
    				obj.put("symbolCode", e.getSymbolCode());
    				obj.put("symbolType", PamsStock.SymbolType.getMsgFromIndex(e.getSymbolType()));
    				obj.put("percent", BigDecimalUtil.generateFormatString(e.getPercent()));
    				array.add(obj);
    			}
    			model.addAttribute("data", array);
    		}
    	}
    	
        return "authc/finance-bar/strategyInfo";
    }
    
    //返回策略中心页面
    @RequestMapping(value = "strategyCenter")
    public String getStrategyCenterPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/finance-bar/strategyCenter";
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
	@RequestMapping(value = "searchUserStrategyInfo")
	public String searchUserStrategyInfo( HttpServletRequest request,
			@RequestParam("strategyType") final Integer strategyType,
			@RequestParam("status") final Integer status,
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
    	
    	List<PamsStrategy> allList = null;
    	if(0 == strategyType && 0 == status) {
    		allList = pamsStrategyService.getPamsStrategyList();
    	} else if(0 == strategyType && 0 != status) {
    		allList = pamsStrategyService.getPamsStrategyListByStatus(status);
    	} else if(0 != strategyType && 0 == status){
    		allList = pamsStrategyService.getPamsStrategyListByStrategyType(strategyType);
    	} else {
    		allList = pamsStrategyService.getPamsStrategyListByStatusAndStrategyType(status, strategyType);
    	}
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsStrategy> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsStrategy strategy : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("strategyId", strategy.getStrategyId());
    		json.put("startDate", strategy.getStartDate());
    		json.put("strategyName", strategy.getStrategyName());
    		json.put("statusName", PamsStrategy.Status.getMsgFromIndex(strategy.getStatus()));
    		json.put("strategyType", PamsStrategy.Type.getMsgFromIndex(strategy.getStrategyType()));
    		BigDecimal avgProfit = strategy.getAvgProfit();
    		if(null != avgProfit) {
    			json.put("avgProfit", avgProfit);
    		} else {
    			json.put("avgProfit", "--");
    		}
    		json.put("targetUserId", strategy.getUserId());
    		json.put("targetUsername", strategy.getUsername());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
    //返回用户气泡图页面
    @RequestMapping(value = "getUserStrategyPicture")
    public String getUserStrategyPicture(HttpServletRequest request, Model model,
    		@RequestParam("targetUserId") final Integer targetUserId,
    		@RequestParam("targetUsername") final String targetUsername
    		){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	List<PamsStrategy> shortList = pamsStrategyService.getPamsStrategyListByStatusAndStrategyTypeAndUserId(
    			PamsStrategy.Status.Closed.getIndex(), PamsStrategy.Type.Short.getIndex(), targetUserId);
    	List<PamsStrategy> mediumList = pamsStrategyService.getPamsStrategyListByStatusAndStrategyTypeAndUserId(
    			PamsStrategy.Status.Closed.getIndex(), PamsStrategy.Type.Medium.getIndex(), targetUserId);
    	List<PamsStrategy> longList = pamsStrategyService.getPamsStrategyListByStatusAndStrategyTypeAndUserId(
    			PamsStrategy.Status.Closed.getIndex(), PamsStrategy.Type.Long.getIndex(), targetUserId);
    	if(CollectionUtils.isNotEmpty(shortList)) {   		
    		JSONArray array = new JSONArray();
    		BigDecimal sum = BigDecimal.ZERO;
    		for(PamsStrategy strategy : shortList) {
    			int count = pamsStrategyService.getStrategyElementListByStrategyId(strategy.getStrategyId()).size();
    			JSONArray obj = new JSONArray();
    			obj.add(DateUtil.getMillionSecondsFromDate(strategy.getStartDate()));
    			sum = sum.add(strategy.getAvgProfit());
    			obj.add(strategy.getAvgProfit());
    			obj.add(count * 30);
    			array.add(obj);
    		}
    		model.addAttribute("shortNum", shortList.size());
    		model.addAttribute("shortData", array);
    		if(sum.compareTo(BigDecimal.ZERO) == 0) {
    			model.addAttribute("shortPercent", 0.00);
    		} else {
    			model.addAttribute("shortPercent", sum.divide(BigDecimal.valueOf(shortList.size()), 
    					2, RoundingMode.HALF_UP));
    		}
    	} else {
    		model.addAttribute("shortNum", 0);
    		model.addAttribute("shortPercent", 0.00);
    		model.addAttribute("shortData", new JSONArray());
    	}
    	
    	if(CollectionUtils.isNotEmpty(mediumList)) {   		
    		JSONArray array = new JSONArray();
    		BigDecimal sum = BigDecimal.ZERO;
    		for(PamsStrategy strategy : mediumList) {
    			int count = pamsStrategyService.getStrategyElementListByStrategyId(strategy.getStrategyId()).size();
    			JSONArray obj = new JSONArray();
    			obj.add(DateUtil.getMillionSecondsFromDate(strategy.getStartDate()));
    			sum = sum.add(strategy.getAvgProfit());
    			obj.add(strategy.getAvgProfit());
    			obj.add(count * 30);
    			array.add(obj);
    		}
    		model.addAttribute("mediumNum", mediumList.size());
    		model.addAttribute("mediumData", array);
    		if(sum.compareTo(BigDecimal.ZERO) == 0) {
    			model.addAttribute("mediumPercent", 0.00);
    		} else {
    			model.addAttribute("mediumPercent", sum.divide(BigDecimal.valueOf(mediumList.size()), 
    					2, RoundingMode.HALF_UP));
    		}
    	} else {
    		model.addAttribute("mediumNum", 0);
    		model.addAttribute("mediumPercent", 0.00);
    		model.addAttribute("mediumData", new JSONArray());
    	}
    	
    	if(CollectionUtils.isNotEmpty(longList)) {   		
    		JSONArray array = new JSONArray();
    		BigDecimal sum = BigDecimal.ZERO;
    		for(PamsStrategy strategy : longList) {
    			int count = pamsStrategyService.getStrategyElementListByStrategyId(strategy.getStrategyId()).size();
    			JSONArray obj = new JSONArray();
    			obj.add(DateUtil.getMillionSecondsFromDate(strategy.getStartDate()));
    			sum = sum.add(strategy.getAvgProfit());
    			obj.add(strategy.getAvgProfit());
    			obj.add(count * 30);
    			array.add(obj);
    		}
    		model.addAttribute("longNum", longList.size());
    		model.addAttribute("longData", array);
    		if(sum.compareTo(BigDecimal.ZERO) == 0) {
    			model.addAttribute("longPercent", 0.00);
    		} else {
    			model.addAttribute("longPercent", sum.divide(BigDecimal.valueOf(longList.size()), 
    					2, RoundingMode.HALF_UP));
    		}
    	} else {
    		model.addAttribute("longNum", 0);
    		model.addAttribute("longPercent", 0.00);
    		model.addAttribute("longData", new JSONArray());
    	}	
    	
        return "authc/finance-bar/userStrategyPicture";
    }
}  