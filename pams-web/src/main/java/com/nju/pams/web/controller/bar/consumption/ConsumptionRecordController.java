package com.nju.pams.web.controller.bar.consumption;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_CONSUMPTION_RECORD)
public class ConsumptionRecordController {  
	
    private static final Logger logger = Logger.getLogger(ConsumptionRecordController.class);
    
    //返回添加消费账目页面
    @RequestMapping(value = "addConsumption")
    public String getAddConsumptionPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "anon/login";
    	}

        return "authc/consumption-bar/addConsumption";
    }
    
    //返回搜索和编辑消费账目页面
    @RequestMapping(value = "searchAndEditConsumption")
    public String getSearchAndEditConsumptionPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "anon/login";
    	}

        return "authc/consumption-bar/searchAndEditConsumption";
    }
}  