package com.nju.pams.web.controller.bar.home;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_HOME_SYSTEM)
public class HomeSystemController {  
    
    private static final Logger logger = Logger.getLogger(HomeSystemController.class);
   
    //返回欢迎页面
    @RequestMapping(value = "welcome")
    public String getWelcomePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/welcome";
    }
    
    //返回通知与公告页面
    @RequestMapping(value = "notice")
    public String getNoticePage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/notice";
    }
    
    //返回最新咨询页面
    @RequestMapping(value = "news")
    public String getNewsPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "error/logout";
    	}
    	
        return "authc/home-bar/news";
    }
}  