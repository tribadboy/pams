package com.nju.pams.web.controller.bar.home;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_HOME_ABOUT)
public class HomeAboutController {  
	
    
    private static final Logger logger = Logger.getLogger(HomeAboutController.class);
    
    //返回系统介绍页面
    @RequestMapping(value = "introduce")
    public String getIntroducePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/introduce";
    }
    
    //返回帮助和反馈页面
    @RequestMapping(value = "help")
    public String getWelcomePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/help";
    }
}  