package com.nju.pams.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsUser;
import com.nju.pams.model.constant.PathConstant;
import com.nju.pams.util.DateUtil;

@Controller
@RequestMapping(value = PathConstant.WEB_AUTHC)
public class WebAuthcController {
	
	@Autowired
	PamsUserService pamsUserService;
	
	private static final Logger logger = Logger.getLogger(WebAuthcController.class);
	
    // 登录成功的页面
    @RequestMapping(value = "home")
    public String adminHomePage(){
        return "authc/home";
    }
    
    //修改用户信息的页面
    @RequestMapping(value = "userInfo")
    public String userInfoPage(HttpServletRequest request, Model model){
    	String username = (String) request.getSession().getAttribute("username");
    	PamsUser pamsUser;
    	if(null == username || null == (pamsUser = pamsUserService.getPamsUserByUsername(username))) {
    		logger.info("注册异常：" + "没有找到username或者对应的user");
   	        return "anon/login";
    	}
    	model.addAttribute("currentDate", DateUtil.getCurrentTime(DateUtil.FormatString));
    	model.addAttribute("pamsUser", pamsUser);
        return "authc/userInfo";
    }

    // 只有角色为admin的才能访问
    @RequiresRoles("vip")
    @RequestMapping(value = "role")
    public String adminWithRole(){
        return "authc/role";
    }

    // 有user:view和user:create权限才能访问
    @RequiresPermissions(value={"vip:create","normal:query"}, logical= Logical.OR)
    @RequestMapping(value = "auth")
    public String adminWithAuth(){
        return "authc/auth";
    }
}