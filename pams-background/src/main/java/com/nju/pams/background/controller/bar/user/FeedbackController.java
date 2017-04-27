package com.nju.pams.background.controller.bar.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.service.PamsFeedbackService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.Feedback;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_USER_FEEDBACK)
public class FeedbackController {  
	
    private static final Logger logger = Logger.getLogger(FeedbackController.class);
    
    @Autowired
    PamsFeedbackService pamsFeedbackService;
    
    //返回查看反馈页面
    @RequestMapping(value = "checkFeedback")
    public String getCheckFeedbackPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
 
        return "authc/user-bar/checkFeedback";
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
	@RequestMapping(value = "searchFeedbackInfo")
	public String searchFeedbackInfo( HttpServletRequest request,
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
    	
    	List<Feedback> allList = null;
    	if(type == 0) {
    		allList = pamsFeedbackService.getAllFeedbackList();
    	} else {
    		allList = pamsFeedbackService.getUncheckedFeedbackList();
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<Feedback> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(Feedback back : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("backId", back.getBackId());
    		json.put("recordTitle", back.getRecordTitle());
    		json.put("username", back.getUsername());
    		json.put("recordDate", back.getRecordDate());
    		json.put("feedTypeName", Feedback.FeedType.getMsgFromTypeStr(back.getFeedTypeStr()));
    		json.put("statusName", Feedback.Status.getMsgFromInt(back.getStatus()));
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
    
	//设置反馈记录为已查看
    @ResponseBody
	@RequestMapping(value = "setCheckedFeedback", method = RequestMethod.POST)
	public String setCheckedFeedback(HttpServletRequest request,
			@RequestParam(value="backId") final Integer backId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	if(null == backId) {
    		ResultUtil.addResult(result, ResultEnum.NullParameter);
    		return result.toString();
    	}
    	
    	Feedback back = pamsFeedbackService.getFeedBackByBackId(backId);
    	if(null == back || back.getStatus() == Feedback.Status.Checked.toIntValue()) {
    		ResultUtil.addResult(result, ResultEnum.FeedbackError);
    		return result.toString();
    	}
    	pamsFeedbackService.setCheckedFeedbackByBackId(backId);
    	ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    
    @RequestMapping(value = "getFeedbackInfo")
    public String getFeedbackInfoPage(HttpServletRequest request, Model model,
    		@RequestParam("backId") final Integer backId){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	Feedback back = pamsFeedbackService.getFeedBackByBackId(backId);
    	if(null != back) {	
    		model.addAttribute("title", back.getRecordTitle());
    		model.addAttribute("username", back.getUsername());
    		model.addAttribute("date", back.getRecordDate());
    		model.addAttribute("feedtypeName", Feedback.FeedType.getMsgFromTypeStr(back.getFeedTypeStr()));
    		model.addAttribute("status", Feedback.Status.getMsgFromInt(back.getStatus()));
    		model.addAttribute("message", back.getMessage());
    	}
    	
        return "authc/user-bar/feedbackInfo";
    }

	
    
}  