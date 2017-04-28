package com.nju.pams.web.controller.bar.consumption;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.consumption.AccountOfDay;
import com.nju.pams.model.consumption.ConsumptionEnum;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import net.sf.json.JSONArray;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_CONSUMPTION_DATA)
public class ConsumptionDataController {  
    
    private static final Logger logger = Logger.getLogger(ConsumptionDataController.class);
    
    @Autowired
  	private PamsAccountService pamsAccountService;

   
    //返回分类占比的雷达对比图
    @RequestMapping(value = "getAllDataOfType")
    public String getAllDataOfTypePage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	JSONArray nameList = new JSONArray();
    	JSONArray userDataList = new JSONArray();
    	JSONArray avgDataList = new JSONArray();
    	
    	BigDecimal allSum = pamsAccountService.getSumCost();
    	BigDecimal userSum = pamsAccountService.getSumCostByUserId(userId);
    	
    	for(ConsumptionEnum c : ConsumptionEnum.values()) {
    		nameList.add(c.getMsg());
    		BigDecimal allItem = pamsAccountService.getSumCostByConsumptionId(c.getCode());
    		BigDecimal userItem = pamsAccountService.getSumCostByConsumptionIdAndUserId(c.getCode(), userId);
    		
    		if(allSum.compareTo(BigDecimal.ZERO) == 0) {
    			avgDataList.add(0);
    		} else {
    			avgDataList.add(allItem.multiply(BigDecimal.valueOf(100)).divide(allSum, 2, RoundingMode.HALF_UP));
    		}     		
    		if(userSum.compareTo(BigDecimal.ZERO) == 0) {
    			userDataList.add(0);
    		} else {
    			userDataList.add(userItem.multiply(BigDecimal.valueOf(100)).divide(userSum, 2, RoundingMode.HALF_UP));
    		}
    	}   	   	
    	
    	model.addAttribute("nameList", nameList);
    	model.addAttribute("userDataList", userDataList);
    	model.addAttribute("avgDataList", avgDataList);
        return "authc/consumption-bar/allDataOfType";
    }
    
    //返回时间分布的区域对比图
    @RequestMapping(value = "getAllDataOfTime")
    public String getAllDataOfTimePage(HttpServletRequest request, Model model,
    		@RequestParam(value="consumptionId", required=false) Integer consumptionId){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	JSONArray avgData = new JSONArray();
    	JSONArray userData = new JSONArray(); 	
    	String minDate = null;
    	String maxDate = null;
    	if(null == consumptionId || 0 == consumptionId) {
    		minDate = pamsAccountService.getMinDateByUserId(userId);
        	maxDate = pamsAccountService.getMaxDateByUserId(userId);	
    	} else {	
    		minDate = pamsAccountService.getMinDateByUserIdAndConsumptionId(userId, consumptionId);
    		maxDate = pamsAccountService.getMaxDateByUserIdAndConsumptionId(userId, consumptionId);
    	}  	
    	
    	if(StringUtils.isNotEmpty(minDate)) {
    		model.addAttribute("year", LocalDate.parse(minDate).getYear());
    		model.addAttribute("month", LocalDate.parse(minDate).getMonthOfYear()-1);
    		model.addAttribute("day", LocalDate.parse(minDate).getDayOfMonth());

    		List<AccountOfDay> userList = null;
    		List<AccountOfDay> avgList = null;
    		if(null == consumptionId || 0 == consumptionId) {
    			model.addAttribute("selectIndex", 0);
    			userList = pamsAccountService.getDaySpendByUserId(userId);
    			avgList = pamsAccountService.getDaySpendInPeriod(minDate, maxDate);
    		} else {
    			model.addAttribute("selectIndex", consumptionId);
    			userList = pamsAccountService.getDaySpendByUserIdAndConsumptionId(userId, consumptionId);
    			avgList = pamsAccountService.getDaySpendInPeriodByConsumptionId(minDate, maxDate, consumptionId);
    		}
    		
    		int length1 = userList.size();
    		String tmp1Date = minDate;
    		for(int i = 0; i < length1; i++) {
    			if(tmp1Date.equals(userList.get(i).getSpendTime())) {
    				userData.add(BigDecimalUtil.generateFormatNumber(userList.get(i).getCost()));
    			} else {
    				userData.add(null);
    				i--;
    			}
    			tmp1Date = LocalDate.parse(tmp1Date).plusDays(1).toString(DateUtil.FormatString);
    		}
    		
    		int length2 = avgList.size();
    		String tmp2Date = minDate;
    		for(int i = 0; i < length2; i++) {
    			if(tmp2Date.equals(avgList.get(i).getSpendTime())) {
    				AccountOfDay daySpend = avgList.get(i);
    				if(daySpend.getCountOfUser() > 0) {
    					BigDecimal avg = daySpend.getCost().divide(BigDecimal.valueOf(
    							daySpend.getCountOfUser()), 2, RoundingMode.HALF_UP);
    					avgData.add(BigDecimalUtil.generateFormatNumber(avg));
    				} else {
    					avgData.add(null);
    				}		
    			} else {
    				avgData.add(null);
    				i--;
    			}
    			tmp2Date = LocalDate.parse(tmp2Date).plusDays(1).toString(DateUtil.FormatString);
    		}		
    	} else {
    		model.addAttribute("year", 2016);
    		model.addAttribute("month", 0);
    		model.addAttribute("day", 1);
    	}
    	
    	model.addAttribute("avgData", avgData);
    	model.addAttribute("userData", userData);  		
        return "authc/consumption-bar/allDataOfTime";
    }
    
   
}  