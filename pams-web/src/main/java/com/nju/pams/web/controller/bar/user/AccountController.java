package com.nju.pams.web.controller.bar.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nju.pams.biz.service.PamsLoginInfoService;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsLoginInfo;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.ResultUtil;
import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_USER_ACCOUNT)
public class AccountController {  
	
    private static final Logger logger = Logger.getLogger(AccountController.class);
    
    @Autowired
    PamsUserService pamsUserService;
    
    @Autowired
    PamsLoginInfoService pamsLoginInfoService;
    
    /**
     * 返回修改信息页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "person-data")
    public String getPersonDataPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	PamsUser user = pamsUserService.getPamsUserByUsername(username);
    	model.addAttribute("user", user);
    	model.addAttribute("registerTime", pamsLoginInfoService.getPamsRegisterTimeByUserId(userId));
    	String jsonStr = getLoginInfo(userId);
    	JSONObject jsonResult = JSONObject.fromObject(jsonStr);
    	System.out.println(jsonResult.get("data"));
    	model.addAttribute("loginInfoList", jsonResult.get("data"));
    	
        return "authc/user-bar/person-data";
    }
    
    /**
     * 获取用户最近的登录信息
     * @param username
     * @return
     */
    @ResponseBody
	@RequestMapping(value = "getLoginInfo")
	public String getLoginInfo(@RequestParam("userId") Integer userId) {
		final JSONObject result = new JSONObject();
		if(null == userId) {
			ResultUtil.addResult(result, ResultEnum.NullParameter);
			return result.toString();
		} 
		String username = pamsUserService.getPamsUserByUserId(userId).getUsername();
		List<PamsLoginInfo> resultList = pamsLoginInfoService.getPamsLoginInfoListByUserId(userId);
		JSONArray array = new JSONArray();
		for(PamsLoginInfo loginInfo : resultList) {
			JSONObject obj = new JSONObject();
			obj.put("username", username);
			obj.put("ip", loginInfo.getIp());
			obj.put("loginTime", loginInfo.getLoginTime());
			array.add(obj);
		}
		result.put("data", array);
		ResultUtil.addSuccess(result);
		return result.toString();
	}
    
    //返回修改信息页面
    @RequestMapping(value = "change-info")
    public String getChangeInfoPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
    	PamsUser user = pamsUserService.getPamsUserByUsername(username);
    	model.addAttribute("user", user);
        return "authc/user-bar/change-info";
    }
}  