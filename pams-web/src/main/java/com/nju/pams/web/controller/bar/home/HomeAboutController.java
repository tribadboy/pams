package com.nju.pams.web.controller.bar.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nju.pams.biz.service.PamsFeedbackService;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.model.system.Feedback;
import com.nju.pams.util.DateUtil;  
  
@Controller  
@RequestMapping(PathConstant.WEB_AUTHC_HOME_ABOUT)
public class HomeAboutController {  
    
    private static final Logger logger = Logger.getLogger(HomeAboutController.class);
    
    @Autowired
    private PamsFeedbackService pamsFeedbackService;
    
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
    
    //添加反馈记录，然后自动返回原页面
    @RequestMapping(value = "addFeedback", method = RequestMethod.POST)
    public String addFixedAsset(Model model, HttpServletRequest request,
     			@RequestParam("recordTitle") final String recordTitle,
     			@RequestParam(value = "feedType", required = false) final List<Integer> feedType,
     			@RequestParam("message") final String message
     			) {

  		String username = (String) request.getSession().getAttribute("username");
      	Integer userId = (Integer) request.getSession().getAttribute("userId");
      	if(null == username || null == userId) {
      		logger.info("session失效，需要用户重新登录");
      		SecurityUtils.getSubject().logout();
     	        return "error/logout";
      	}
      	String recordDate = LocalDate.now().toString(DateUtil.FormatString);
      	StringBuffer feedTypeStr = new StringBuffer();
      	if(CollectionUtils.isNotEmpty(feedType)) {
      		for(Integer i : feedType) {
      			feedTypeStr.append(i + ",");
      		}
      		feedTypeStr.deleteCharAt(feedTypeStr.length() - 1);
      	} else {
      		feedTypeStr.append(Feedback.NoTypeStr);
      	}
      	Feedback feedback = new Feedback(userId, username, recordTitle, recordDate, feedTypeStr.toString(), message);
      	pamsFeedbackService.insertLoanRecord(feedback);
      	
      	model.addAttribute("msg", "添加固定资产记录成功");     	
        return "authc/home-bar/help";
    }    
}  