package com.nju.pams.background.controller.bar.system;


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
import com.nju.pams.biz.service.PamsNoticeAndInformService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.PamsNotice;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_SYSTEM_NOTICE)
public class NoticeController {  
	
    private static final Logger logger = Logger.getLogger(NoticeController.class);
    
    @Autowired
	PamsNoticeAndInformService pamsNoticeAndInformService;
    
    //返回创建公告页面
    @RequestMapping(value = "makeNotice")
    public String getMakeNoticePage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/system-bar/makeNotice";
    }
    
    //返回公告查询页面
    @RequestMapping(value = "checkNotice")
    public String getCheckNoticePage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	PamsNotice notice = pamsNoticeAndInformService.getNewestValidPamsNotice();
    	if(null == notice) {
    		model.addAttribute("noticeMessage", PamsNotice.DefaultMessage);
    	} else {
    		model.addAttribute("noticeMessage", notice.getMessage());
    	}

        return "authc/system-bar/checkNotice";
    }
    
    //添加新闻记录，然后自动返回添加页面
	@RequestMapping(value = "add", method = RequestMethod.POST)
   	public String addNotice(Model model, HttpServletRequest request,
   			@RequestParam("recordDate") final String recordDate,
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
    	
    	PamsNotice notice = new PamsNotice(recordDate, message);
    	pamsNoticeAndInformService.insertPamsNotice(notice);
    	model.addAttribute("msg", "添加公告成功");
    	
        return "authc/system-bar/makeNotice";
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
	@RequestMapping(value = "searchNoticeInfo")
	public String searchNoticeInfo( HttpServletRequest request,
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
    	
    	List<PamsNotice> allList = null;
    	if(type == 0) {
    		allList = pamsNoticeAndInformService.getAllPamsNotice();
    	} else {
    		allList = pamsNoticeAndInformService.getAllValidPamsNotice();
    	}
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsNotice> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsNotice notice : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("noticeId", notice.getNoticeId());
    		json.put("recordDate", notice.getRecordDate());
    		json.put("statusName", PamsNotice.Status.getMsgFromInt(notice.getStatus()));
    		json.put("message", notice.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除公告
    @ResponseBody
	@RequestMapping(value = "deleteNoticeInfo", method = RequestMethod.POST)
	public String deleteNoticeInfo(HttpServletRequest request,
			@RequestParam(value="noticeId") final Integer noticeId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	pamsNoticeAndInformService.deletePamsNoticeByNoticeId(noticeId);
    
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
   //将公告设置为已结束
    @ResponseBody
	@RequestMapping(value = "finishNoticeInfo", method = RequestMethod.POST)
	public String finishNoticeInfo(HttpServletRequest request,
			@RequestParam(value="noticeId") final Integer noticeId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	PamsNotice notice = pamsNoticeAndInformService.getPamsNoticeByNoticeId(noticeId);
    	if(notice == null || notice.getStatus() == PamsNotice.Status.Invalid.toIntValue()) {
    		ResultUtil.addResult(result, ResultEnum.NoticeError);
			return result.toString();
    	}
    	
    	notice.setStatus(PamsNotice.Status.Invalid.toIntValue());
    	pamsNoticeAndInformService.updatePamsNotice(notice);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
  
    
}  