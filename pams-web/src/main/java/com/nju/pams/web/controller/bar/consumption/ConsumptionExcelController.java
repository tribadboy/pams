package com.nju.pams.web.controller.bar.consumption;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.model.constant.PathConstant;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_CONSUMPTION_EXCEL)
public class ConsumptionExcelController {  
    
    private static final Logger logger = Logger.getLogger(ConsumptionExcelController.class);
   
    //返回每日消费报表页面
    @RequestMapping(value = "dateExcel")
    public String getDateExcelPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "anon/login";
    	}
    	
        return "authc/consumption-bar/dateExcel";
    }
    
    //返回月份消费报表页面
    @RequestMapping(value = "monthExcel")
    public String getMonthExcelPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "anon/login";
    	}
    	
        return "authc/consumption-bar/monthExcel";
    }
    
    //返回年度消费报表页面
    @RequestMapping(value = "yearExcel")
    public String getYearExcelPage(HttpServletRequest request){
    	String username = (String) request.getSession().getAttribute("username");
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
    	if(null == username || null == userId) {
    		logger.info("session失效，需要用户重新登录");
    		SecurityUtils.getSubject().logout();
   	        return "anon/login";
    	}
    	
        return "authc/consumption-bar/yearExcel";
    }
   
}  