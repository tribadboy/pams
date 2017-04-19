package com.nju.pams.web.controller.bar.consumption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.service.PamsAccountMonthService;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.consumption.AccountOfMonth;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;
import com.nju.pams.model.consumption.ConsumptionEnum;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.constant.CommonTime;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_CONSUMPTION_EXCEL)
public class ConsumptionExcelController {  
    
    private static final Logger logger = Logger.getLogger(ConsumptionExcelController.class);
    
    @Autowired
  	private PamsAccountService pamsAccountService;
    
    @Autowired
    private PamsAccountMonthService pamsAccountMonthService;
   
    //返回每日消费报表页面
    @RequestMapping(value = "dateExcel")
    public String getDateExcelPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/consumption-bar/dateExcel";
    }
    
    //返回月份消费报表页面
    @RequestMapping(value = "monthExcel")
    public String getMonthExcelPage(HttpServletRequest request, Model model,
    		@RequestParam(value="spendMonth", required=false) String spendMonth){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	if(null == spendMonth) {
    		//点击月份报表访问，没有spendMonth，默认取上个月,否则取表单中提交的月份作为默认值
    		spendMonth = LocalDate.now().minusMonths(1).toString(DateUtil.FormatString).substring(0, 7); 		
    	}
    	
    	List<AccountOfMonth> accountMonthList = pamsAccountMonthService
				.getAccountOfMonthListByCertainMonth(userId, spendMonth);
		//将list转化成以type为key的map结构
		Map<Integer, AccountOfMonth> typeMap = new HashMap<Integer, AccountOfMonth>();
		if(CollectionUtils.isNotEmpty(accountMonthList)) {
			for(AccountOfMonth am : accountMonthList) {
				typeMap.put(am.getConsumptionId(), am);
			}
		}
    	
    	JSONArray nameList = new JSONArray();
    	JSONArray valueList = new JSONArray();
    	JSONArray mapList = new JSONArray();
    	for(ConsumptionEnum c : ConsumptionEnum.values()) {
    		BigDecimal value;
    		if(typeMap.containsKey(c.getCode())) {
    			value = BigDecimalUtil.generateFormatNumber(typeMap.get(c.getCode()).getCost());
    		} else {
    			value = BigDecimal.valueOf(0.0);
    		}
    		nameList.add(c.getMsg());
    		valueList.add(value);
    		JSONArray array = new JSONArray();
    		array.add(c.getMsg());
    		array.add(value);
    		mapList.add(array);
    	}
    	
    	model.addAttribute("spendMonth", spendMonth);
    	model.addAttribute("nameList", nameList);
    	model.addAttribute("valueList", valueList);
    	model.addAttribute("mapList", mapList);
    	
        return "authc/consumption-bar/monthExcel";
    }
    
    //返回年度消费报表页面
    @RequestMapping(value = "yearExcel")
    public String getYearExcelPage(HttpServletRequest request, Model model,
    		@RequestParam(value="spendYear", required=false) String spendYear){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	if(null == spendYear) {
    		//点击年度报表访问，没有spendYear，默认取去年,否则取表单中提交的年份作为默认值
    		spendYear = LocalDate.now().minusYears(1).toString(DateUtil.FormatString).substring(0, 4); 		
    	}
    	
    	JSONArray nameArray = new JSONArray();
    	Map<Integer, JSONObject> map = new LinkedHashMap<Integer, JSONObject>();
    	for(ConsumptionEnum con : ConsumptionEnum.values()) {
    		JSONObject json = new JSONObject();
    		JSONArray monthData = new JSONArray();
    		json.put("name", con.getMsg());
    		json.put("data", monthData);
    		map.put(con.getCode(), json);
    	}
    	
    	for(CommonTime.Month month : CommonTime.Month.values()) {
    		nameArray.add(month.getMessage());
    		String spendMonth = spendYear + "-" + month.getName();
    		List<AccountOfMonth> accountMonthList = pamsAccountMonthService.getAccountOfMonthListByCertainMonth(userId, spendMonth);
    		//将list转化成以type为key的map结构
    		Map<Integer, AccountOfMonth> typeMap = new HashMap<Integer, AccountOfMonth>();
    		if(CollectionUtils.isNotEmpty(accountMonthList)) {
    			for(AccountOfMonth am : accountMonthList) {
    				typeMap.put(am.getConsumptionId(), am);
    			}
    		}
    		for(ConsumptionEnum con : ConsumptionEnum.values()) {
    			BigDecimal value;
    			if(typeMap.containsKey(con.getCode())) {
    				value = BigDecimalUtil.generateFormatNumber(typeMap.get(con.getCode()).getCost());
    			} else {
    				value = BigDecimal.valueOf(0.0);
    			}
    			JSONArray tmp = (JSONArray) (map.get(con.getCode()).get("data"));
    			tmp.add(value);
    		}
    	}
    	JSONArray resultArray = new JSONArray();
    	for(JSONObject json : map.values()) {
    		resultArray.add(json);
    	}
    	model.addAttribute("spendYear", spendYear);
    	model.addAttribute("nameArray", nameArray);
    	model.addAttribute("resultArray", resultArray);
    	
        return "authc/consumption-bar/yearExcel";
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
	@RequestMapping(value = "searchConsumptionInfoForDateExcel")
	public String searchConsumptionInfoForDateExcel( HttpServletRequest request,
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

    	ConsumptionCondition condition = new ConsumptionCondition(userId, null, startDate, endDate, null, null);
    	List<ConsumptionAccount> allList = pamsAccountService.selectAccountByCondition(condition);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<ConsumptionAccount> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(ConsumptionAccount account : resultList) {
    		JSONObject json = new JSONObject();
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
   
}  