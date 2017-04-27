package com.nju.pams.background.controller.bar.system;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nju.pams.biz.service.PamsNoticeAndInformService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.PamsInform;
import com.nju.pams.model.system.PamsNotice;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_SYSTEM_INFORM)
public class InformController {  
	
    private static final Logger logger = Logger.getLogger(InformController.class);
    
    @Autowired
	PamsNoticeAndInformService pamsNoticeAndInformService;
    
    //返回创建通知页面
    @RequestMapping(value = "makeInform")
    public String getMakeInformPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/system-bar/makeInform";
    }
    
    //返回通知查询页面
    @RequestMapping(value = "checkInform")
    public String getCheckInformPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}

        return "authc/system-bar/checkInform";
    }
    
    //添加新闻记录，然后自动返回添加页面
	@RequestMapping(value = "add", method = RequestMethod.POST)
   	public String addInform(Model model, HttpServletRequest request,
   			@RequestParam("informTypeId") final Integer informTypeId,
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
    	
    	PamsInform inform = new PamsInform(informTypeId, recordDate, message);
    	pamsNoticeAndInformService.insertInform(inform);
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
	@RequestMapping(value = "searchInformInfo")
	public String searchInformInfo( HttpServletRequest request,
   			@RequestParam("type") final Integer type,
   			@RequestParam("informTypeId") final Integer informTypeId,
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
    	
    	List<PamsInform> allList = null;
    	if(type == 0) {
    		if(informTypeId == PamsInform.InformType.Total.toIntValue()) {
    			allList = pamsNoticeAndInformService.getAllTypeTotalPamsInforms();
    		} else {
    			allList = pamsNoticeAndInformService.getAllTypeSpecialPamsInforms();
    		}
    	} else {
    		if(informTypeId == PamsInform.InformType.Total.toIntValue()) {
    			allList = pamsNoticeAndInformService.getValidTypeTotalPamsInforms();
    		} else {
    			allList = pamsNoticeAndInformService.getValidTypeSpecialPamsInforms();
    		}
    	}
    	
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsInform> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsInform inform : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("informId", inform.getInformId());
    		json.put("recordDate", inform.getRecordDate());
    		json.put("statusName", PamsInform.Status.getMsgFromInt(inform.getStatus()));
    		json.put("typeName", PamsInform.InformType.getMsgFromInt(inform.getInformTypeId()));
    		json.put("message", inform.getMessage());
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//删除通知
    @ResponseBody
	@RequestMapping(value = "deleteInformInfo", method = RequestMethod.POST)
	public String deleteInformInfo(HttpServletRequest request,
			@RequestParam(value="informId") final Integer informId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	}
    	
    	pamsNoticeAndInformService.deletePamsInformByInformId(informId);
    
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
   //将通知设置为已结束
    @ResponseBody
	@RequestMapping(value = "finishInformInfo", method = RequestMethod.POST)
	public String finishInformInfo(HttpServletRequest request,
			@RequestParam(value="informId") final Integer informId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	} 	

    	pamsNoticeAndInformService.setInformInvalid(informId);

		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    //查询通知指定的用户
   	@RequestMapping(value = "getUserInfo", method = RequestMethod.POST)
    public String getUserInfoPage(Model model, HttpServletRequest request,
      			@RequestParam("informId") final Integer informId
      			) {

   		String username = (String) request.getSession().getAttribute("username");
       	Integer userId = (Integer) request.getSession().getAttribute("userId");
       	if(null == username || null == userId) {
       		logger.info("session失效，需要用户重新登录");
       		SecurityUtils.getSubject().logout();
      	        return "error/logout";
       	}
       	
       	model.addAttribute("informId", informId);
        return "authc/system-bar/userInfo";
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
	@RequestMapping(value = "searchInformUserDataInfo")
	public String searchInformUserDataInfo( HttpServletRequest request,
			@RequestParam("informId") final Integer informId,
			@RequestParam(value = "nameKey", required = false) String nameKey,
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
    	
    	List<PamsUser> allList;
    	if(StringUtils.isEmpty(nameKey)) {
    		allList = pamsUserService.getPamsUserList();
    	} else {
    		try {
    			nameKey = URLDecoder.decode(nameKey, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				logger.info("模糊查询的关键词解码异常");
	    		SecurityUtils.getSubject().logout();
	   	        result.put("rows", "[]");
	   	        result.put("results", 0);
	   	        result.put("hasError", true);
	   	        result.put("error", "模糊查询的关键词解码异常");
	   	        return result.toString();
			}
    		allList = pamsUserService.getPamsUsersByKey(nameKey);
    	}
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsUser> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsUser user : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("username", user.getUsername());
    		json.put("phone", user.getPhone());
    		json.put("mail", user.getMail());
    		json.put("status", PamsUser.Status.getMsgFromIndex(user.getStatus()));
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
}  