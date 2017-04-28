package com.nju.pams.background.controller.bar.user;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.nju.pams.biz.service.PamsRoleService;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsRole;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_USER_PERMISSION)
public class PermissionController {  
	
    private static final Logger logger = Logger.getLogger(PermissionController.class);
    
    @Autowired
    PamsUserService pamsUserService;
    
    @Autowired
    PamsRoleService pamsRoleService;
    
    //返回创建通知页面
    @RequestMapping(value = "editPermission")
    public String getEditPermissionPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
        return "authc/user-bar/editPermission";
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
	@RequestMapping(value = "searchUserPermissionInfo")
	public String searchUserPermissionInfo( HttpServletRequest request,
			@RequestParam("roleIndex") final Integer roleIndex,
			@RequestParam(value = "nameKey", required = false) String nameKey,
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
    	String roleName = PamsRole.RoleType.getNameFromIndex(roleIndex);
    	int total = allList.size();
    	int end = (start + limit > total) ? total : start + limit;
    	List<PamsUser> resultList = allList.subList(start, end);
    	JSONArray array = new JSONArray();
    	for(PamsUser user : resultList) {
    		JSONObject json = new JSONObject();
    		json.put("userId", user.getUserId());
    		json.put("username", user.getUsername());
    		json.put("status", PamsUser.Status.getMsgFromIndex(user.getStatus()));	
    		List<PamsRole> hasRoleList = pamsRoleService.listRolesForUserByUsername(user.getUsername());
    		boolean flag = false;
    		for(PamsRole hasRole : hasRoleList) {
    			if(hasRole.getRoleName().equals(roleName)) {
    				flag = true;
    				break;
    			}
    		}
    		if(flag) {
    			json.put("flagName", "是");
    		} else {
    			json.put("flagName", "否");
    		}
    		array.add(json);
    	}
    	result.put("rows", array);
	    result.put("results", total);
	    result.put("hasError", false);
    	
    	return result.toString();
	}	
	
	//赋予用户权限
    @ResponseBody
	@RequestMapping(value = "setYesForUser", method = RequestMethod.POST)
	public String setYesForUser(HttpServletRequest request,
			@RequestParam(value="roleIndex") final Integer roleIndex,
			@RequestParam(value="targetUserId") final Integer targetUserId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	} 	
    	
    	String roleName = PamsRole.RoleType.getNameFromIndex(roleIndex);
    	String targetUsername = pamsUserService.getPamsUserByUserId(targetUserId).getUsername();
    	List<PamsRole> hasRoleList = pamsRoleService.listRolesForUserByUsername(targetUsername);
    	if(CollectionUtils.isNotEmpty(hasRoleList)) {
    		for(PamsRole hasRole : hasRoleList) {
    			if(hasRole.getRoleName().equals(roleName)) {
    				ResultUtil.addSuccess(result);
    				return result.toString();
    			}
    		}
    	}  	
    	
    	PamsRole role = pamsRoleService.getPamsRoleByRoleName(roleName);
    	List<PamsRole> rolesList = Arrays.asList(role);
    	pamsRoleService.addRolesListForUser(targetUserId, rolesList);

		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    //取消用户权限
    @ResponseBody
	@RequestMapping(value = "setNoForUser", method = RequestMethod.POST)
	public String setNoForUser(HttpServletRequest request,
			@RequestParam(value="roleIndex") final Integer roleIndex,
			@RequestParam(value="targetUserId") final Integer targetUserId
			) {
    	
		final JSONObject result = new JSONObject();	
		String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		ResultUtil.addResult(result, ResultEnum.SessionClose);
			return result.toString();
    	} 	

    	String roleName = PamsRole.RoleType.getNameFromIndex(roleIndex);
    	PamsRole role = pamsRoleService.getPamsRoleByRoleName(roleName);
    	List<PamsRole> rolesList = Arrays.asList(role);
    	pamsRoleService.deleteRolesListForUser(targetUserId, rolesList);	

		ResultUtil.addSuccess(result);
		return result.toString();
	}
}  