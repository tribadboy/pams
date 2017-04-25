package com.nju.pams.web.controller.bar.asset;


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

import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.model.asset.RegularIncome;
import com.nju.pams.model.constant.PathConstant;

import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_ASSET_REGULAR_INCOME)
public class RegularIncomeController {  
	
    private static final Logger logger = Logger.getLogger(RegularIncomeController.class);
    
    @Autowired
	 private PamsRegularIncomeService pamsRegularIncomeService;
    
    //返回添加常规收入页面
    @RequestMapping(value = "addRegularIncome")
    public String getAddRegularIncomePage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/asset-bar/addRegularIncome";
    }
    
    //返回搜索和编辑常规收入页面
    @RequestMapping(value = "searchAndEditRegularIncome")
    public String getSearchAndEditRegularIncomePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}

        return "authc/asset-bar/searchAndEditRegularIncome";
    }
    
    //添加常规收入页面，然后自动返回添加页面
	@RequestMapping(value = "add", method = RequestMethod.POST)
   	public String addRegularIncome(Model model, HttpServletRequest request,
   			@RequestParam("recordTime") final String recordTime,
   			@RequestParam("recordAmount") final Double recordAmount,
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
    	
    	RegularIncome regularIncome = new RegularIncome(userId, BigDecimalUtil.generateFormatNumber(recordAmount), recordTime, message);
    	pamsRegularIncomeService.insertRegularIncome(regularIncome);
    	model.addAttribute("msg", "添加常规收入记录成功");
    	
        return "authc/asset-bar/addRegularIncome";
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
	@RequestMapping(value = "searchRegularIncomeInfo")
	public String searchRegularIncomeInfo( HttpServletRequest request,
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
    	
    	List<RegularIncome> allList = pamsRegularIncomeService.getRegularIncomeListByUserIdInPeriodTime(userId, startDate, endDate);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<RegularIncome> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(RegularIncome income : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("incomeId", income.getIncomeId());
    		json.put("username", username);
    		json.put("recordAmount", income.getRecordAmount());
    		json.put("recordTime", income.getRecordTime());
    		json.put("message", income.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除固定资产记录
    @ResponseBody
	@RequestMapping(value = "deleteRegularIncomeInfo", method = RequestMethod.POST)
	public String deleteRegularIncomeInfo(HttpServletRequest request,
			@RequestParam(value="incomeIds") final List<Integer> incomeIds
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(incomeIds)) {
    		for(Integer id : incomeIds) {
    			pamsRegularIncomeService.deleteRegularIncomeByIncomeId(id);
    		}
    	}	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
}  