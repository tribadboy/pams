package com.nju.pams.web.controller.bar.consumption;


import java.util.Arrays;
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

import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;
import com.nju.pams.model.consumption.ConsumptionEnum;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_CONSUMPTION_RECORD)
public class ConsumptionRecordController {  
	
    private static final Logger logger = Logger.getLogger(ConsumptionRecordController.class);
    
    @Autowired
	 private PamsAccountService pamsAccountService;
    
    //返回添加消费账目页面
    @RequestMapping(value = "addConsumption")
    public String getAddConsumptionPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/consumption-bar/addConsumption";
    }
    
    //返回搜索和编辑消费账目页面
    @RequestMapping(value = "searchAndEditConsumption")
    public String getSearchAndEditConsumptionPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}

        return "authc/consumption-bar/searchAndEditConsumption";
    }
    
    //添加消费账目，然后自动返回添加页面
	@RequestMapping(value = "add", method = RequestMethod.POST)
   	public String addConsumption(Model model, HttpServletRequest request,
   			@RequestParam("consumptionId") final Integer consumptionId,
   			@RequestParam("spendTime") final String spendTime,
   			@RequestParam("cost") final Double cost,
   			@RequestParam("message") final String message
   			) {
		model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	ConsumptionAccount consumption = new ConsumptionAccount(userId,
    			consumptionId,BigDecimalUtil.generateFormatNumber(cost),
    			spendTime,message);
    	pamsAccountService.insertConsumptionAccount(consumption);
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
	@ResponseBody
	@RequestMapping(value = "searchConsumptionInfo")
	public String searchConsumptionInfo( HttpServletRequest request,
   			@RequestParam("consumptionId") final Integer consumptionId,
   			@RequestParam("startDate") final String startDate,
   			@RequestParam("endDate") final String endDate,
   			@RequestParam("minCost") final Double minCost,
   			@RequestParam("maxCost") final Double maxCost,
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
    	
    	List<Integer> consumptionIds = null;
		if(consumptionId != 0) {
			consumptionIds = Arrays.asList(consumptionId);
		} 
    	ConsumptionCondition condition = new ConsumptionCondition(userId, consumptionIds, startDate, endDate,
    			BigDecimalUtil.generateFormatNumber(minCost), BigDecimalUtil.generateFormatNumber(maxCost));
    	List<ConsumptionAccount> allList = pamsAccountService.selectAccountByCondition(condition);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<ConsumptionAccount> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(ConsumptionAccount account : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("accountId", account.getAccountId());
    		json.put("username", username);
    		json.put("consumptionName", ConsumptionEnum.getMsgFromInt(account.getConsumptionId()));
    		json.put("cost", account.getCost());
    		json.put("spendTime", account.getSpendTime());
    		json.put("message", account.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除消费账目
    @ResponseBody
	@RequestMapping(value = "deleteConsumptionInfo", method = RequestMethod.POST)
	public String deleteConsumptionInfo(HttpServletRequest request,
			@RequestParam(value="accountIds") final List<Integer> accountIds
			) {
    	
		final JSONObject result = new JSONObject();
		
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(accountIds)) {
    		for(Integer id : accountIds) {
    			pamsAccountService.deleteConsumptionAccountByAccountId(id);
    		}
    	}	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
}  