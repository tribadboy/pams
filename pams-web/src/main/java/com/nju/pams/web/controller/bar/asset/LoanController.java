package com.nju.pams.web.controller.bar.asset;


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

import com.nju.pams.biz.service.PamsLoanService;
import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.asset.LoanRecord;
import com.nju.pams.model.constant.PathConstant;

import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_ASSET_LOAN)
public class LoanController {  
	
    private static final Logger logger = Logger.getLogger(LoanController.class);
    
    @Autowired
	 private PamsLoanService pamsLoanService;
    
    //返回创建贷款页面
    @RequestMapping(value = "addLoanRecordPage")
    public String getAddLoanRecordPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/asset-bar/addLoanRecord";
    }
    
    //返回贷入与贷出页面
    @RequestMapping(value = "editLoanRecordPage")
    public String getEditLoanRecordPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	model.addAttribute("todayStr", LocalDate.now().toString(DateUtil.FormatString));
        return "authc/asset-bar/editLoanRecord";
    }
    
    //返回搜索和编辑贷款页面
    @RequestMapping(value = "loanChange")
    public String getLoanChangePage(HttpServletRequest request, Model model,
    		@RequestParam(value = "loanId", required = false) final Integer loanId){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    
    	LoanRecord record = pamsLoanService.getLoanRecordByLoanId(loanId);
    	model.addAttribute("direction", LoanRecord.Direction.getMsgFromInt(record.getDirection()));
    	model.addAttribute("exceptRepayAmount", BigDecimalUtil.generateFormatNumber(record.getExceptRepayAmount()));
    	model.addAttribute("realRepayAmount", pamsLoanService.computeSumAmountInRealLoanChange(record.getLoanId()));
    	model.addAttribute("statusName", LoanRecord.Status.getMsgFromInt(record.getStatus()));
    	model.addAttribute("loanId", loanId);
        return "authc/asset-bar/loanChange";
    }
    
    //创建贷款，然后自动返回添加页面
	@RequestMapping(value = "addLoanRecord", method = RequestMethod.POST)
   	public String addLoanRecord(Model model, HttpServletRequest request,
   			@RequestParam("direction") final Integer direction,
   			@RequestParam("changeAmount") final Double changeAmount,
   			@RequestParam("changeTime") final String changeTime,
   			@RequestParam("exceptRepayAmount") final Double exceptRepayAmount,
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
    	
    	LoanRecord loanRecord = new LoanRecord(userId, direction, BigDecimalUtil.generateFormatNumber(exceptRepayAmount), message);
    	pamsLoanService.makeLoanRecord(loanRecord, BigDecimalUtil.generateFormatNumber(changeAmount), changeTime);
    	model.addAttribute("msg", "创建存款成功");
        return "authc/asset-bar/addLoanRecord";
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
	@RequestMapping(value = "searchLoanRecordInfo")
	public String searchLoanRecordInfo( HttpServletRequest request,
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
    	
    	List<LoanRecord> allList = null;
    	if(type == 0) {
    		allList = pamsLoanService.getAllLoanRecordsByUserId(userId);
    	} else {
    		allList = pamsLoanService.getValidLoanRecordsByUserId(userId);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<LoanRecord> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(LoanRecord record : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("loanId", record.getLoanId());
    		json.put("username", username);
    		json.put("direction", LoanRecord.Direction.getMsgFromInt(record.getDirection()));
    		json.put("exceptRepayAmount", BigDecimalUtil.generateFormatNumber(record.getExceptRepayAmount()));
    		json.put("realRepayAmount", pamsLoanService.computeSumAmountInRealLoanChange(record.getLoanId()));
    		json.put("statusName", LoanRecord.Status.getMsgFromInt(record.getStatus()));
    		json.put("message", record.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除贷款记录和相应的变更记录
    @ResponseBody
	@RequestMapping(value = "deleteLoanRecordInfo", method = RequestMethod.POST)
	public String deleteLoanRecordInfo(HttpServletRequest request,
			@RequestParam(value="loanIds") final List<Integer> loanIds
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(loanIds)) {
    		for(Integer id : loanIds) {
    			pamsLoanService.deleteLoanRecordAndChange(id);
    		}
    	}	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
	//关闭单条贷款记录
    @ResponseBody
	@RequestMapping(value = "closeLoanRecordInfo", method = RequestMethod.POST)
	public String closeLoanRecordInfo(HttpServletRequest request,
			@RequestParam(value="loanIds") final List<Integer> loanIds,
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
    		ResultUtil.addResult(result, ResultEnum.CloseLoanError);
        	return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(loanIds)) {
    		int id = loanIds.get(0);
//    		if(pamsDepositService.checkDateValid(id, closeTime)) {
//    			pamsDepositService.closeDepositRecord(id, closeTime);
//    			ResultUtil.addSuccess(result);
//    			return result.toString();
//    		}
    		pamsLoanService.closeLoanRecord(id);
    		ResultUtil.addSuccess(result);
    		return result.toString();
    	}	
    	ResultUtil.addResult(result, ResultEnum.CloseLoanError);
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
	@RequestMapping(value = "searchLoanChangeInfo")
	public String searchLoanChangeInfo( HttpServletRequest request,
   			@RequestParam("loanId") final Integer loanId,
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
    	
    	List<LoanChange> allList = pamsLoanService.getLoanChangeListByLoanId(loanId);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<LoanChange> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(LoanChange change : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("changeId", change.getChangeId());
    		json.put("changeTypeName", LoanChange.ChangeType.getMsgFromInt(change.getChangeTypeId()));
    		json.put("changeAmount", BigDecimalUtil.generateFormatNumber(change.getChangeAmount()));
    		json.put("changeTime", change.getChangeTime());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
    //创建贷款还款记录，然后自动返回添加页面
	@RequestMapping(value = "addLoanChange", method = RequestMethod.POST)
   	public String addLoanChange(Model model, HttpServletRequest request,
   			@RequestParam("loanId2") final Integer loanId2,
   			@RequestParam("changeTime") final String changeTime,
   			@RequestParam("changeAmount") final Double changeAmount
   			) {
		model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	LoanRecord record = pamsLoanService.getLoanRecordByLoanId(loanId2);	
    	if(record.getStatus() == LoanRecord.Status.InValid.getIndex()) {
    		model.addAttribute("msg", "该贷款已经结束，创建还款记录失败");
    	} else {
    		LoanChange loanChange = new LoanChange(loanId2, BigDecimalUtil.generateFormatNumber(changeAmount), changeTime);
        	pamsLoanService.insertLoanChange(loanChange);
        	model.addAttribute("msg", "创建还款记录成功");
    	}
    	model.addAttribute("loanId", loanId2);
    	model.addAttribute("direction", LoanRecord.Direction.getMsgFromInt(record.getDirection()));
    	model.addAttribute("exceptRepayAmount", BigDecimalUtil.generateFormatNumber(record.getExceptRepayAmount()));
    	model.addAttribute("realRepayAmount", pamsLoanService.computeSumAmountInRealLoanChange(record.getLoanId()));
    	model.addAttribute("statusName", LoanRecord.Status.getMsgFromInt(record.getStatus()));
        return "authc/asset-bar/loanChange";
   	}

	//删除贷款的还款记录
    @ResponseBody
	@RequestMapping(value = "deleteLoanChangeInfo", method = RequestMethod.POST)
	public String deleteLoanChangeInfo(HttpServletRequest request,
			@RequestParam(value="changeIds") final List<Integer> changeIds
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	if(CollectionUtils.isNotEmpty(changeIds)) {
    		int changeId = changeIds.get(0);
    		if(pamsLoanService.getLoanChangeByChangeId(changeId).getChangeTypeId() 
    				== LoanChange.ChangeType.MakeLoan.getIndex()) {
    			ResultUtil.addResult(result, ResultEnum.DeleteLoanChangeError);
    			return result.toString();
    		}
    		pamsLoanService.deleteLoanChange(changeId);
    	}	
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
}  