package com.nju.pams.web.controller.bar.asset;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.nju.pams.biz.service.PamsDepositService;
import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.asset.DepositRecord;
import com.nju.pams.model.asset.DepositTimeEnum;
import com.nju.pams.model.constant.PathConstant;

import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_ASSET_DEPOSIT)
public class DepositController {  
	
    private static final Logger logger = Logger.getLogger(DepositController.class);
    
    @Autowired
	 private PamsDepositService pamsDepositService;
    
    //返回创建存款页面
    @RequestMapping(value = "addDepositRecordPage")
    public String getAddDepositRecordPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}

        return "authc/asset-bar/addDepositRecord";
    }
    
    //返回搜索和编辑存款页面
    @RequestMapping(value = "inflowAndOutflow")
    public String getInflowAndOutflowPage(HttpServletRequest request, Model model,
    		@RequestParam(value = "depositId", required = false) final Integer depositId){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	List<DepositRecord> allList = pamsDepositService.getAllDepositRecordsByUserId(userId);
    	List<String> idList = new ArrayList<String>();
    	List<String> nameList = new ArrayList<String>();
    	if(CollectionUtils.isNotEmpty(allList)) {
    		for(DepositRecord record : allList) {
    			idList.add("\"" + record.getDepositId() + "\"");
    			nameList.add("\"" + record.getDepositName() + "\"");
    		}
    		if(null == depositId) {
    			model.addAttribute("depositId", idList.get(0));
    		} else {
    			model.addAttribute("depositId", depositId);
    		} 		
    	} else {
    		model.addAttribute("depositId", -1);
    	}
    	model.addAttribute("idList", idList);
    	model.addAttribute("nameList", nameList);
    	model.addAttribute("length", idList.size());
        return "authc/asset-bar/inflowAndOutflow";
    }
    
    //返回转入与转出页面
    @RequestMapping(value = "editDepositRecordPage")
    public String getEditDepositRecordPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	model.addAttribute("todayStr", LocalDate.now().toString(DateUtil.FormatString));
        return "authc/asset-bar/editDepositRecord";
    }
    
    //创建存款，然后自动返回添加页面
	@RequestMapping(value = "addDepositRecord", method = RequestMethod.POST)
   	public String addDepositRecord(Model model, HttpServletRequest request,
   			@RequestParam("depositName") final String depositName,
   			@RequestParam("depositTimeId") final Integer depositTimeId,
   			@RequestParam("currentProfitPercent") final Float currentProfitPercent,
   			@RequestParam("fixedProfitPercent") final Float fixedProfitPercent,
   			@RequestParam("changeTime") final String changeTime,
   			@RequestParam("changeAmount") final Double changeAmount,
   			@RequestParam("message") final String message
   			) {

		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	DepositRecord depositRecord = new DepositRecord(userId, depositName, depositTimeId, currentProfitPercent, 
        		fixedProfitPercent, message);
    	pamsDepositService.makeDepositRecord(depositRecord, BigDecimalUtil.generateFormatNumber(changeAmount), changeTime);
    	model.addAttribute("msg", "创建存款成功");
        return "authc/asset-bar/addDepositRecord";
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
	@RequestMapping(value = "searchDepositRecordInfo")
	public String searchDepositRecordInfo( HttpServletRequest request,
   			@RequestParam("type") final Integer type,
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
    	
    	List<DepositRecord> allList = null;
    	if(type == 0) {
    		allList = pamsDepositService.getAllDepositRecordsByUserId(userId);
    	} else {
    		allList = pamsDepositService.getValidDepositRecordsByUserId(userId);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<DepositRecord> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(DepositRecord record : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("depositId", record.getDepositId());
    		json.put("depositName", record.getDepositName());
    		json.put("depositTimeName", DepositTimeEnum.getTimeFromIndex(record.getDepositTimeId()).getMsg());
    		json.put("currentProfitPercent", BigDecimalUtil.generateFormatNumber(record.getCurrentProfitPercent()));
    		json.put("fixedProfitPercent", BigDecimalUtil.generateFormatNumber(record.getFixedProfitPercent()));
    		json.put("currentAmount", pamsDepositService.computeDepositRecordValue(record.getDepositId(), 
    				LocalDate.now().toString(DateUtil.FormatString)));
    		json.put("statusName", DepositRecord.Status.getMsgFromInt(record.getStatus()));
    		json.put("message", record.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除存款记录和相应的变更记录
    @ResponseBody
	@RequestMapping(value = "deleteDepositRecordInfo", method = RequestMethod.POST)
	public String deleteDepositRecordInfo(HttpServletRequest request,
			@RequestParam(value="depositIds") final List<Integer> depositIds
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(depositIds)) {
    		for(Integer id : depositIds) {
    			pamsDepositService.deleteDepositRecordAndChange(id);
    		}
    	}	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
	//关闭单条存款记录
    @ResponseBody
	@RequestMapping(value = "closeDepositRecordInfo", method = RequestMethod.POST)
	public String closeDepositRecordInfo(HttpServletRequest request,
			@RequestParam(value="depositIds") final List<Integer> depositIds,
			@RequestParam(value="closeTime") final String closeTime
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(StringUtils.isEmpty(closeTime)) {
    		ResultUtil.addResult(result, ResultEnum.CloseDepositError);
        	return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(depositIds)) {
    		int id = depositIds.get(0);
    		if(pamsDepositService.checkDateValid(id, closeTime)) {
    			pamsDepositService.closeDepositRecord(id, closeTime);
    			ResultUtil.addSuccess(result);
    			return result.toString();
    		}
    	}	
    	ResultUtil.addResult(result, ResultEnum.CloseDepositError);
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
	@RequestMapping(value = "searchDepositChangeInfo")
	public String searchDepositChangeInfo( HttpServletRequest request,
   			@RequestParam("depositId") final Integer depositId,
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
    	
    	List<DepositChange> allList = pamsDepositService.getDepositChangeListByDepositId(depositId);  	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<DepositChange> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(DepositChange change : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("depositName", pamsDepositService.getDepositRecordByDepositId(depositId).getDepositName());
    		json.put("changeTypeName", DepositChange.ChangeType.getMsgFromInt(change.getChangeTypeId()));
    		json.put("changeAmount", BigDecimalUtil.generateFormatNumber(change.getChangeAmount()));
    		json.put("changeTime", change.getChangeTime());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
    //创建存款交易，然后自动返回添加页面
	@RequestMapping(value = "addDepositChange", method = RequestMethod.POST)
   	public String addDepositChange(Model model, HttpServletRequest request,
   			@RequestParam("depositId2") final Integer depositId2,
   			@RequestParam("changeTypeId") final Integer changeTypeId,
   			@RequestParam("changeTime") final String changeTime,
   			@RequestParam("changeAmount") final Double changeAmount
   			) {

		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	//DepositRecord depositRecord = new DepositRecord(userId, depositName, depositTimeId, currentProfitPercent, 
        //		fixedProfitPercent, message);
    	//pamsDepositService.makeDepositRecord(depositRecord, BigDecimalUtil.generateFormatNumber(changeAmount), changeTime);
    	
    	List<DepositRecord> allList = pamsDepositService.getAllDepositRecordsByUserId(userId);
    	List<String> idList = new ArrayList<String>();
    	List<String> nameList = new ArrayList<String>();
    	if(CollectionUtils.isNotEmpty(allList)) {
    		for(DepositRecord record : allList) {
    			idList.add("\"" + record.getDepositId() + "\"");
    			nameList.add("\"" + record.getDepositName() + "\"");
    		}  				
    	} 
    	model.addAttribute("depositId", depositId2);	
    	model.addAttribute("idList", idList);
    	model.addAttribute("nameList", nameList);
    	model.addAttribute("length", idList.size());
    	
    	if(!pamsDepositService.checkDateValid(depositId2, changeTime)) {
    		model.addAttribute("msg", "创建失败，请检查存款的状态和交易日期");
    		return "authc/asset-bar/inflowAndOutflow";
    	}
    	if(changeTypeId == DepositChange.ChangeType.Outflow.toIntValue()) {
    		BigDecimal maxAmount = pamsDepositService.getMaxValidAmountForOutflow(depositId2, changeTime);
    		if(BigDecimalUtil.generateFormatNumber(changeAmount).compareTo(maxAmount) > 0) {
    			model.addAttribute("msg", "创建失败，该周期内的本金为" 
    					+ BigDecimalUtil.generateFormatString(maxAmount) + "元，超出转出数额，请考虑全部转出");
    	        return "authc/asset-bar/inflowAndOutflow";
    		}
    	}
    	DepositChange depositChange = new DepositChange(depositId2, changeTypeId, 
    			BigDecimalUtil.generateFormatNumber(changeAmount), changeTime);
    	pamsDepositService.insertDepositChangeForInflowAndOutflow(depositChange);
    	model.addAttribute("msg", "创建转入或转出记录成功");
        return "authc/asset-bar/inflowAndOutflow";
   	}

	
    
}  